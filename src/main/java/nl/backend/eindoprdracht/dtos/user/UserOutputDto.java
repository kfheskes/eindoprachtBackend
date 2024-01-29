package nl.backend.eindoprdracht.dtos.user;

import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindoprdracht.dtos.manageraccount.ManagerAccountOutputDto;
import nl.backend.eindoprdracht.dtos.role.RoleOutputDto;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class UserOutputDto {

    private Long Id;
    private String username;

    private boolean enabled;

    private String fName;
    private String mName;
    private String lName;
    private Date dob;
    private String address;
    private String houseNumber;
    private String zipcode;

    private String residence;
    private String pNumber;
    private String email;

    private Set<RoleOutputDto> roles;
    private EmployeeAccountOutputDto employeeAccountOutputDto;

    private ManagerAccountOutputDto managerAccountOutputDto;

    private CustomerAccountOutputDto customerAccountOutputDto;

    public void setEmployeeAccountOutput(EmployeeAccountOutputDto employeeAccountOutputDto) {
        this.employeeAccountOutputDto = employeeAccountOutputDto;
    }


}
