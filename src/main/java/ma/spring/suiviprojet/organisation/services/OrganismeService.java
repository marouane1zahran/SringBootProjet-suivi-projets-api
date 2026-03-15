package ma.spring.suiviprojet.organisation.services;

import ma.spring.suiviprojet.organisation.entity.Organisme;
import ma.spring.suiviprojet.organisation.repository.OrganismeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OrganismeService {
    @Autowired
    private OrganismeRepository repository;

    public List<Organisme> getAll() {
        return repository.findAll();
    }

    public Organisme save(Organisme organisme) {
        return repository.save(organisme);
    }

    public Organisme getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}