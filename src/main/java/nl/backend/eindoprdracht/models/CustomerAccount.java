package nl.backend.eindoprdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "customer_accounts")
public class CustomerAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String fName;
    private String mName;
    private String lName;

    private String address;
    private String zipcode;
    private String pNumber;
    private String companyName;
    private String contract;
    private Double balans;

    @OneToMany(mappedBy = "customerAccount")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "customerAccount")
    private Set<Invoice> invoices = new HashSet<>();

    // TODO: list of invoice
    public CustomerAccount(long id, String fName, String mName, String lName, String address, String zipcode, String pNumber, String companyName, String contract, Double balans) {
        this.id = id;
        this.fName = fName;
        this.mName = mName;
        this.lName = lName;
        this.address = address;
        this.zipcode = zipcode;
        this.pNumber = pNumber;
        this.companyName = companyName;
        this.contract = contract;
        this.balans = balans;
    }

    public CustomerAccount() {
    }



}
