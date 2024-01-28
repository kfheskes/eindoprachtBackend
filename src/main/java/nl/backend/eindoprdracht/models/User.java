package nl.backend.eindoprdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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

    @Column(nullable = false)
    private boolean enabled = true;

    //TODO alle overeenkomende data in user en extra info in entity.
    @OneToOne( cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_account_id")
    private EmployeeAccount employeeAccount;
    //TODO one to one relaties aanleggen met verschillende accounts:

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_account_id")
    private ManagerAccount managerAccount;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public User(){

    }

}
