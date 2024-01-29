package nl.backend.eindoprdracht.dtos.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
public class UserInputDto {
    @NotBlank(message = "Username is required")
    public String username;
    @NotBlank(message = "Password is required")
    public String password;

    public boolean enabled;
    public String[] roles;

    public String fName;

    public String mName;
    public String lName;
    public Date dob;
    public String address;
    public String houseNumber;
    public String zipcode;
    public String residence;
    public String pNumber;
    public String email;

}
