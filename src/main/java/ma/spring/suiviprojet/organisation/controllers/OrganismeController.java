package ma.spring.suiviprojet.organisation.controllers;

import ma.spring.suiviprojet.organisation.entity.Organisme;
import ma.spring.suiviprojet.organisation.service.OrganismeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/organismes")
public class OrganismeController {
    @Autowired
    private OrganismeService service;

    @GetMapping
    public List<Organisme> getAll() { return service.findAll(); }

    @PostMapping
    public Organisme create(@RequestBody Organisme o) { return service.save(o); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}