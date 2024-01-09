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

    //TODO moet er een woonplaats komen naast zipcode en address
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

    private String responsibilities;

    @ManyToOne
    @JoinColumn(name = "work_schedule_id")
    private WorkSchedule workSchedule;

    @ManyToMany(mappedBy = "managers")
    private Set<Order> orders = new HashSet<>();

    public ManagerAccount(long id, String fName, String mName, String lName, Date dob, String address, String zipcode, String pNumber, String responsibilities) {
        this.id = id;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.dob = dob;
        this.address = address;
        this.zipcode = zipcode;
        this.pNumber = pNumber;
        this.responsibilities = responsibilities;
    }

    public ManagerAccount(){
    }
}
