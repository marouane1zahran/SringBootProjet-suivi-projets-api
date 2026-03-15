package ma.spring.suiviprojet.organisation.service;

import ma.spring.suiviprojet.organisation.entity.Profil;
import ma.spring.suiviprojet.organisation.repository.ProfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProfilService {
    @Autowired
    private ProfilRepository profilRepository;

    public List<Profil> findAll() { return profilRepository.findAll(); }
    public Profil save(Profil profil) { return profilRepository.save(profil); }
}