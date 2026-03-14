package ma.spring.suiviprojet.organisation.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;

    public Employe create(Employe employe){
        return employeRepository.save(employe);
    }

    public List<Employe> getAll(){
        return employeRepository.findAll();
    }

    public Employe getById(Integer id){
        return employeRepository.findById(id).orElse(null);
    }

    public void delete(Integer id){
        employeRepository.deleteById(id);
    }

}