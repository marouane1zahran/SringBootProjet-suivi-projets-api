package ma.spring.suiviprojet.organisation.controller;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.service.EmployeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employes")
@RequiredArgsConstructor
public class EmployeController {

    private final EmployeService employeService;

    @PostMapping
    public Employe create(@RequestBody Employe employe){
        return employeService.create(employe);
    }

    @GetMapping
    public List<Employe> getAll(){
        return employeService.getAll();
    }

    @GetMapping("/{id}")
    public Employe getById(@PathVariable Integer id){
        return employeService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        employeService.delete(id);
    }

}