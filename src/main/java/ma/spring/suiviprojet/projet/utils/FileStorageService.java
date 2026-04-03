package ma.spring.suiviprojet.projet.utils;

import jakarta.annotation.PostConstruct;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    // On récupère le chemin du dossier depuis application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    private Path fileStorageLocation;

    // @PostConstruct s'exécute automatiquement au démarrage de Spring Boot
    @PostConstruct
    public void init() {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            // Crée le dossier "uploads/documents" s'il n'existe pas déjà sur votre PC
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Impossible de créer le répertoire où les fichiers téléchargés seront stockés.", ex);
        }
    }

    /**
     * Sauvegarde le fichier et retourne son nouveau nom unique
     */
    public String sauvegarderFichier(MultipartFile file) {
        // 1. On nettoie le nom original du fichier (pour éviter les failles de sécurité)
        String nomOriginal = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Vérification de sécurité basique
            if (nomOriginal.contains("..")) {
                throw new RegleMetierException("Désolé ! Le nom du fichier contient une séquence de chemin invalide : " + nomOriginal);
            }

            // 2. Générer un nom de fichier unique avec UUID (très important !)
            // Pourquoi ? Si deux utilisateurs envoient un fichier "cahier-des-charges.pdf", le second écrasera le premier.
            String extension = "";
            if (nomOriginal.contains(".")) {
                extension = nomOriginal.substring(nomOriginal.lastIndexOf("."));
            }
            String nouveauNom = UUID.randomUUID().toString() + extension;

            // 3. On copie le fichier dans le dossier cible
            Path targetLocation = this.fileStorageLocation.resolve(nouveauNom);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            // 4. On retourne le nouveau nom unique pour pouvoir le sauvegarder dans la base de données (Document.chemin)
            return nouveauNom;

        } catch (IOException ex) {
            throw new RegleMetierException("Impossible de stocker le fichier " + nomOriginal + ". Veuillez réessayer !");
        }
    }

    // --- POUR LE TÉLÉCHARGEMENT (DOWNLOAD) ---
    public Resource loadFileAsResource(String nomFichier) {
        try {
            Path filePath = this.fileStorageLocation.resolve(nomFichier).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RegleMetierException("Fichier physique introuvable : " + nomFichier);
            }
        } catch (MalformedURLException ex) {
            throw new RegleMetierException("Erreur de lecture du fichier : " + nomFichier);
        }
    }

    // --- POUR LA SUPPRESSION ---
    public void supprimerFichier(String nomFichier) {
        try {
            Path filePath = this.fileStorageLocation.resolve(nomFichier).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException ex) {
            throw new RegleMetierException("Impossible de supprimer le fichier physique : " + nomFichier);
        }
    }
}