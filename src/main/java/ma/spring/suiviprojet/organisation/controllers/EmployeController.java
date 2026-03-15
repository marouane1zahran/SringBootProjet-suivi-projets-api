package ma.spring.suiviprojet.organisation.controllers;

import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/employes")
public class EmployeController {
    @Autowired
    private EmployeService employeService;

    @GetMapping
    public List<Employe> getAll() { return employeService.findAll(); }

    @PostMapping
    public Employe create(@RequestBody Employe employe) { return employeService.save(employe); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { employeService.delete(id); }
}