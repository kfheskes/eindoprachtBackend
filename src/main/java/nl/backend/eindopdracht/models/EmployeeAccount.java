package nl.backend.eindopdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

//TODO kijk naar de datums dattype de data wilt meestal YYYY-MAAND-DAG dit moet anders

@Getter
@Setter

@Entity
@Table(name = "employee_accounts")
public class EmployeeAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    @Column(name = "contract_h")
    private Double contractH;
    private LocalDate startContract;

    @OneToOne (mappedBy = "employeeAccount", cascade = CascadeType.ALL, orphanRemoval = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;

    @ManyToMany(mappedBy = "employees")
    private Set<Order> orders = new HashSet<>();

    public EmployeeAccount(long id,  Double contractH, LocalDate startContract) {
        this.id = id;

        this.contractH = contractH;
        this.startContract = startContract;
    }

    public EmployeeAccount(){
    }
}
