package nl.backend.eindopdracht.services;


import nl.backend.eindopdracht.dtos.role.RoleOutputDto;
import nl.backend.eindopdracht.models.Role;
import nl.backend.eindopdracht.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class RoleService {

    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleOutputDto> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        List<RoleOutputDto> dtoList = new ArrayList<>();
        for (Role role : roles) {
            dtoList.add(roleTransferToDto(role));
        }
        return dtoList;
    }

    public RoleOutputDto roleTransferToDto(Role role) {

        RoleOutputDto dto = new RoleOutputDto();

        dto.setId(role.getId());
        dto.setRolename(role.getRoleName());

        return dto;
    }

}
