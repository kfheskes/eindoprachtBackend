package nl.backend.eindoprdracht.dtos.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInputDto {

    public String username;

    public String password;

    public String[] roles;

}
