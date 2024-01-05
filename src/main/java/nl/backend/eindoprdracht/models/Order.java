package nl.backend.eindoprdracht.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter

@Entity
@Table (name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    private String typeOfWork;
    private Integer amount;
    private Double price;
    private Integer productId;
    private String productName;
    private String customerName;
    private String status;
    private LocalDate dateCreated;
    private LocalTime time;
    private String workAddress;
    private String workZipcode;

    @OneToOne
    private Invoice invoice;



    public Order(Long id, String typeOfWork, int amount, Double price, int productId, String productName, String customerName, String status, LocalDate dateCreated, LocalTime time, String workAddress, String workZipcode) {
        this.id = id;
        this.typeOfWork = typeOfWork;
        this.amount = amount;
        this.price = price;
        this.productId = productId;
        this.productName = productName;
        this.customerName = customerName;
        this.status = status;
        this.dateCreated = dateCreated;
        this.time = time;
        this.workAddress = workAddress;
        this.workZipcode = workZipcode;
    }

public Order(){

}


}
