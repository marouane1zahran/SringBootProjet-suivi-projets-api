package ma.spring.suiviprojet.organisation.service;

import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.exceptions.RegleMetierException;
import ma.spring.suiviprojet.organisation.dto.EmployeCreateDTO;
import ma.spring.suiviprojet.organisation.dto.EmployeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.entity.Profil;
import ma.spring.suiviprojet.organisation.mapper.EmployeMapper;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import ma.spring.suiviprojet.organisation.repository.ProfilRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final ProfilRepository profilRepository;
    private final EmployeMapper employeMapper;
    private final PasswordEncoder passwordEncoder;

    // --- CRÉATION ---
    public EmployeResponseDTO create(EmployeCreateDTO dto) {
        Employe employe = employeMapper.toEntity(dto);


        Profil profil = profilRepository.findById(dto.getProfilId().intValue())
                .orElseThrow(() -> new RegleMetierException("Le profil spécifié n'existe pas avec l'ID : " + dto.getProfilId()));

        String motDePasseHache = passwordEncoder.encode(dto.getPassword());
        employe.setPassword(motDePasseHache);

        employe.setProfil(profil);
        Employe saved = employeRepository.save(employe);
        return employeMapper.toDTO(saved);
    }

    // --- LECTURE (Tous) ---
    @Transactional(readOnly = true)
    public List<EmployeResponseDTO> getAll() {
        return employeRepository.findAll()
                .stream()
                .map(employeMapper::toDTO)
                .toList();
    }

    // --- LECTURE (Un seul) ---
    @Transactional(readOnly = true)
    public EmployeResponseDTO getById(Integer id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Employé introuvable avec l'ID : " + id));
        return employeMapper.toDTO(employe);
    }

    // --- MODIFICATION ---
    public EmployeResponseDTO update(Integer id, EmployeCreateDTO dto) {
        Employe employeExistant = employeRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Employé introuvable avec l'ID : " + id));

        Profil profil = profilRepository.findById(dto.getProfilId().intValue())
                .orElseThrow(() -> new RegleMetierException("Le profil spécifié n'existe pas."));


        String loginActuel = employeExistant.getLogin();
        String passwordActuel = employeExistant.getPassword();

        employeMapper.updateEntityFromDto(dto, employeExistant);

        if (dto.getLogin() == null || dto.getLogin().trim().isEmpty()) {
            employeExistant.setLogin(loginActuel);
        }

        if (dto.getPassword() == null || dto.getPassword().trim().isEmpty()) {
            employeExistant.setPassword(passwordActuel);
        } else {

             employeExistant.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        // 5. Mise à jour de la relation Profil
        employeExistant.setProfil(profil);

        return employeMapper.toDTO(employeRepository.save(employeExistant));
    }

    // --- SUPPRESSION ---
    public void delete(Integer id) {
        Employe employe = employeRepository.findById(id)
                .orElseThrow(() -> new RegleMetierException("Employé introuvable avec l'ID : " + id));

        // Note de Tech Lead (marouane zahran): Si l'employé est déjà affecté à une phase,
        // la base de données (JPA) bloquera la suppression pour protéger les données.
        employeRepository.delete(employe);
    }
}