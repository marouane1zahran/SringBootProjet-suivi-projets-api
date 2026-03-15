package ma.spring.suiviprojet.organisation.service;


import lombok.RequiredArgsConstructor;
import ma.spring.suiviprojet.organisation.dto.EmployeCreateDTO;
import ma.spring.suiviprojet.organisation.dto.EmployeResponseDTO;
import ma.spring.suiviprojet.organisation.entity.Employe;
import ma.spring.suiviprojet.organisation.entity.Profil;
import ma.spring.suiviprojet.organisation.mapper.EmployeMapper;
import ma.spring.suiviprojet.organisation.repository.EmployeRepository;
import ma.spring.suiviprojet.organisation.repository.ProfilRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

@RequiredArgsConstructor
public class EmployeService {

    private final EmployeRepository employeRepository;
    private final ProfilRepository profilRepository;
    private final EmployeMapper employeMapper;

    public EmployeResponseDTO create(EmployeCreateDTO dto){

        Employe employe = employeMapper.toEntity(dto);

        Profil profil = profilRepository.findById(dto.getProfilId().intValue())
                .orElseThrow(() -> new RuntimeException("Profil not found"));

        employe.setProfil(profil);

        Employe saved = employeRepository.save(employe);

        return employeMapper.toDTO(saved);
    }

    public List<EmployeResponseDTO> getAll(){
        return employeRepository.findAll()
                .stream()
                .map(employeMapper::toDTO)
                .toList();
    }
}