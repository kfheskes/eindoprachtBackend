package nl.backend.eindoprdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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

    private Double contractH;
    private Date startContract;

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
