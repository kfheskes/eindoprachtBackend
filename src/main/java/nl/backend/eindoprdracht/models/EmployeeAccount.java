package nl.backend.eindoprdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
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

    private String fName;
    private String mName;
    private String lName;
    private Date dob;

    private String address;
    private String zipcode;
    private String pNumber;

    @Column(name = "contract_h")
    private Double contractH;
    private Date startContract;

    //TODO omschrijven waarom CasdadeType wordt gebruikt
    @OneToOne (mappedBy = "employeeAccount", cascade = CascadeType.ALL)
    private User user;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;

    @ManyToMany(mappedBy = "employees")
    private Set<Order> orders = new HashSet<>();

    public EmployeeAccount(long id, String fName, String mName, String lName, Date dob, String address, String zipcode, String pNumber, Double contractH, Date startContract) {
        this.id = id;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.dob = dob;
        this.address = address;
        this.zipcode = zipcode;
        this.pNumber = pNumber;
        this.contractH = contractH;
        this.startContract = startContract;
    }

    public EmployeeAccount(){
    }
}
