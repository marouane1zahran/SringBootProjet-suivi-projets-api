package ma.spring.suiviprojet.organisation.controllers;

import ma.spring.suiviprojet.organisation.entity.Profil;
import ma.spring.suiviprojet.organisation.service.ProfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/profils")
public class ProfilController {
    @Autowired
    private ProfilService profilService;

    @GetMapping
    public List<Profil> getAll() { return profilService.findAll(); }

    @PostMapping
    public Profil create(@RequestBody Profil profil) { return profilService.save(profil); }
}