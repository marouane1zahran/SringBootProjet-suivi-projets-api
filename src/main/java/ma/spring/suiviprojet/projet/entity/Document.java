package ma.spring.suiviprojet.projet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String code;

    @Column(nullable = false)
    private String libelle;

    private String description;

    @Column(nullable = false)
    private String chemin; // L'URL ou le chemin local du fichier




    @ManyToOne
    @JoinColumn(name = "projet_id", nullable = false)
    private Projet projet;
}