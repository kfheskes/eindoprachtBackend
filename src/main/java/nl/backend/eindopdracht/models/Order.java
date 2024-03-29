package nl.backend.eindopdracht.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindopdracht.utils.TypeOfWork;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter

@Entity
@Table(name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private TypeOfWork typeOfWork;
    private Integer amount;
    private Double price;
    private String productName;
    private String status;
    private LocalDate dateCreated;
    private LocalTime time;
    private String workAddress;
    private String workZipcode;

    @OneToOne
    @JoinColumn(name = "invoice_combi")
    private Invoice invoice;

    @ManyToMany
    @JoinTable(name = "orders_employees", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "employee_id"))


    private Set<EmployeeAccount> employees = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "orders_managers", joinColumns = @JoinColumn(name = "order_id"), inverseJoinColumns = @JoinColumn(name = "manager_id"))

    private Set<ManagerAccount> managers = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "customer_account_id")
    private CustomerAccount customerAccount;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    private List<File> fileList;

    public Order(Long id, TypeOfWork typeOfWork, int amount, Double price, String productName, String status, LocalDate dateCreated, LocalTime time, String workAddress, String workZipcode) {
        this.id = id;
        this.typeOfWork = typeOfWork;
        this.amount = amount;
        this.price = price;
        this.productName = productName;
        this.status = status;
        this.dateCreated = dateCreated;
        this.time = time;
        this.workAddress = workAddress;
        this.workZipcode = workZipcode;
    }

    public Order() {

    }


}
