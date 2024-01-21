package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.role.RoleInputDto;
import nl.backend.eindoprdracht.dtos.role.RoleOutputDto;
import nl.backend.eindoprdracht.models.Role;
import nl.backend.eindoprdracht.repositories.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class RoleService {

    private final RoleRepository roleRepository;


    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<RoleOutputDto> getAllRoles(){
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
        dto.setRolename(role.getRolename());

        return dto;
    }

    public Role dtoTransfertoRole(RoleInputDto roleDto) {

        Role role = new Role();

        role.setRolename(roleDto.getRolename());

        return role;
    }
}
