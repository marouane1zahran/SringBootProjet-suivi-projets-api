package ma.spring.suiviprojet.facturation.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.facturation.dto.FactureCreateDTO;
import ma.spring.suiviprojet.facturation.dto.FactureResponseDTO;
import ma.spring.suiviprojet.facturation.entity.Facture;
import ma.spring.suiviprojet.facturation.mapper.FactureMapper;
import ma.spring.suiviprojet.facturation.repository.FactureRepository;
import ma.spring.suiviprojet.projet.entity.Phase;
import ma.spring.suiviprojet.projet.repository.PhaseRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FactureService {

    private final FactureRepository factureRepository;
    private final PhaseRepository phaseRepository;
    private final FactureMapper factureMapper;

    // Créer une facture pour une phase
    public FactureResponseDTO facturer(FactureCreateDTO dto) {

        // 1. Vérifier que la phase existe
        Phase phase = phaseRepository.findById(dto.getPhaseId().intValue())
                .orElseThrow(() -> new RuntimeException("Phase introuvable"));

        // 2. Vérifier que la phase est terminée
        if (!phase.getEtatRealisation()) {
            throw new RuntimeException("La phase n'est pas encore terminée");
        }

        // 3. Vérifier que la phase n'est pas déjà facturée
        if (phase.getEtatFacturation()) {
            throw new RuntimeException("Cette phase est déjà facturée");
        }

        // 4. Créer la facture
        Facture facture = new Facture();
        facture.setCode("FAC-" + System.currentTimeMillis());
        facture.setDateFacture(LocalDate.now());
        facture.setPhase(phase);

        // 5. Mettre à jour l'état de facturation de la phase
        phase.setEtatFacturation(true);
        facture.setMontant(phase.getMontant());
        // 6. Sauvegarder la facture
        Facture saved = factureRepository.save(facture);

        // 7. Retourner la réponse
        return factureMapper.toDto(saved);
    }

    // Récupérer toutes les factures
    public List<FactureResponseDTO> getAll() {
        return factureRepository.findAll()
                .stream()
                .map(factureMapper::toDto)
                .collect(Collectors.toList());
    }

    // Récupérer une facture par ID
    public FactureResponseDTO getById(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));
        return factureMapper.toDto(facture);
    }

    // Supprimer une facture et libérer la phase
    public void delete(Long id) {
        Facture facture = factureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        // On remet la phase à "non facturée" pour pouvoir la refacturer plus tard
        Phase phase = facture.getPhase();
        phase.setEtatFacturation(false);
        phaseRepository.save(phase);

        // On supprime la facture
        factureRepository.deleteById(id);
    }
    public void encaisserPaiement(Long factureId) {
        Facture facture = factureRepository.findById(factureId)
                .orElseThrow(() -> new RuntimeException("Facture introuvable"));

        // On récupère la phase associée
        Phase phase = facture.getPhase();

        // On marque la phase comme payée
        phase.setEtatPaiement(true);

        phaseRepository.save(phase);
    }
}