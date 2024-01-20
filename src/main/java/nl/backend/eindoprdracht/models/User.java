package nl.backend.eindoprdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Role getRole(){
        return role;
    }
}
