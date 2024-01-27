package nl.backend.eindoprdracht.dtos.user;

import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindoprdracht.dtos.role.RoleOutputDto;

import java.util.Set;

@Getter
@Setter
public class UserOutputDto {

    private Long Id;
    private String username;

    private boolean enabled;
    private Set<RoleOutputDto> roles;
    private EmployeeAccountOutputDto employeeAccountOutputDto;
    public void setEmployeeAccountOutput(EmployeeAccountOutputDto employeeAccountOutputDto){
    this.employeeAccountOutputDto = employeeAccountOutputDto;
}




}
