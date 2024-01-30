package nl.backend.eindoprdracht.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate dob;
    public String address;
    public String houseNumber;
    public String zipcode;
    public String residence;
    public String pNumber;
    @NotBlank
    @Email
    public String email;

}
