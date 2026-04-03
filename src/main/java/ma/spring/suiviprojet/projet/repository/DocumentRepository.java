package ma.spring.suiviprojet.projet.repository;

import ma.spring.suiviprojet.projet.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {


    List<Document> findByProjetId(Integer projetId);
}