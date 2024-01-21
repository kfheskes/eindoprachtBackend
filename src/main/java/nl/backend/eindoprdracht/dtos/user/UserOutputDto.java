package nl.backend.eindoprdracht.dtos.user;

import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.role.RoleOutputDto;

import java.util.Set;

@Getter
@Setter
public class UserOutputDto {

    private Long Id;
    private String username;


    private Set<RoleOutputDto> roles;

}
