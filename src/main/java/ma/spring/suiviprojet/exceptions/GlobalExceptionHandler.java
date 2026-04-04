package ma.spring.suiviprojet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Intercepter les erreurs métier personnalisées
    @ExceptionHandler(RegleMetierException.class)
    public ResponseEntity<Map<String, Object>> handleRegleMetierException(RegleMetierException ex) {
        Map<String, Object> erreurFormattee = new HashMap<>();
        erreurFormattee.put("timestamp", LocalDateTime.now());
        erreurFormattee.put("statut", HttpStatus.BAD_REQUEST.value());
        erreurFormattee.put("erreur", "Erreur Règle Métier");
        erreurFormattee.put("message", ex.getMessage());

        return new ResponseEntity<>(erreurFormattee, HttpStatus.BAD_REQUEST);
    }

    // 2. Intercepter les erreurs de @Valid (ex: @NotBlank, @NotNull)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> erreurs = new HashMap<>();
        erreurs.put("timestamp", LocalDateTime.now());
        erreurs.put("statut", HttpStatus.BAD_REQUEST.value());
        erreurs.put("erreur", "Erreur de Validation des données");

        // On extrait chaque champ qui a posé problème et on liste son message d'erreur
        Map<String, String> detailsChamps = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String nomChamp = ((FieldError) error).getField();
            String messageErreur = error.getDefaultMessage();
            detailsChamps.put(nomChamp, messageErreur);
        });

        erreurs.put("details", detailsChamps);

        return new ResponseEntity<>(erreurs, HttpStatus.BAD_REQUEST);
    }

    // 3. Intercepter les erreurs système inattendues (NullPointerException, base de données coupée...)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        Map<String, Object> erreurFormattee = new HashMap<>();
        erreurFormattee.put("timestamp", LocalDateTime.now());
        erreurFormattee.put("statut", HttpStatus.INTERNAL_SERVER_ERROR.value());
        erreurFormattee.put("erreur", "Erreur Interne du Serveur");
        // En production, on évite d'exposer ex.getMessage() pour des raisons de sécurité,
        // mais pour le développement c'est très utile.
        erreurFormattee.put("message", "Une erreur inattendue s'est produite : " + ex.getMessage());

        return new ResponseEntity<>(erreurFormattee, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}