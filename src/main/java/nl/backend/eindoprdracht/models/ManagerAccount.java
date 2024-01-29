package nl.backend.eindoprdracht.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "manager_accounts")
public class ManagerAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private String responsibilities;

    @OneToOne(mappedBy = "managerAccount", cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;

    @ManyToMany(mappedBy = "managers")
    private Set<Order> orders = new HashSet<>();

    public ManagerAccount(long id, String responsibilities) {
        this.id = id;
        this.responsibilities = responsibilities;
    }

    public ManagerAccount(){
    }
}
