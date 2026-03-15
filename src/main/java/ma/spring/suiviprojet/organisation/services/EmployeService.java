package ma.spring.suiviprojet.organisation.service;

import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EmployeService {
    @Autowired
    private EmployeRepository repository;

    public List<Employe> findAll() { return repository.findAll(); }
    public Employe save(Employe e) { return repository.save(e); }
    public void delete(Long id) { repository.deleteById(id); }
}