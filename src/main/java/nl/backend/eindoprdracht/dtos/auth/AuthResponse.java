package nl.backend.eindoprdracht.dtos.auth;


//TODO is deze classe nodig?
public class AuthResponse {

    private final String jwt;

    public AuthResponse(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }


}
