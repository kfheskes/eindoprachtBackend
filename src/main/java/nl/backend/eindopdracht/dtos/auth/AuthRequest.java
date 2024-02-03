package nl.backend.eindopdracht.dtos.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequest {
    public String username;
    public String password;


}
