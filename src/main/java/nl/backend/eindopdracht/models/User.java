package nl.backend.eindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    private String fName;
    private String mName;
    private String lName;
    private String email;

    private LocalDate dob;
    private String address;
    private String zipcode;
    private String residence;
    private String pNumber;
    private String houseNumber;
    @Column(nullable = false)
    private boolean enabled = true;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_account_id")
    private EmployeeAccount employeeAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_account_id")
    private ManagerAccount managerAccount;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_account_id")
    private CustomerAccount customerAccount;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Set<Role> getRoles() {
        return roles;
    }

    public User() {

    }

}
