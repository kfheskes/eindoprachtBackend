package nl.backend.eindoprdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    private String companyName;
    private String contract;
    private Double balans;

    @OneToOne(mappedBy = "customerAccount", cascade = CascadeType.ALL)
    private User user;

    @OneToMany(mappedBy = "customerAccount")
    private Set<Order> orders = new HashSet<>();

    @OneToMany(mappedBy = "customerAccount")
    @JsonIgnore
    private Set<Invoice> invoices = new HashSet<>();



    public CustomerAccount(long id, String companyName, String contract, Double balans) {
        this.id = id;
        this.companyName = companyName;
        this.contract = contract;
        this.balans = balans;
    }

    public CustomerAccount() {
    }



}
