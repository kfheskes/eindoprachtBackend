package nl.backend.eindoprdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

    @Getter
    @Setter
    @Entity
    @Table(name = "invoices")
    public class Invoice {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)

        private Long id;
        private String typeOfWork;
        private Double price;
        private String typeOfProduct;
        private String businessTaxNumber;
        private int amount;
        private long taxAmount;
        private LocalDate date;
        private String businessAddress;
        private String customerAddress;
        private String termOfPayment;
        public Invoice(Long id, String typeOfWork, Double price, String typeOfProduct, String businessTaxNumber, int amount, long taxAmount, LocalDate date, String businessAddress, String customerAddress, String termOfPayment) {
            this.id = id;
            this.typeOfWork = typeOfWork;
            this.price = price;
            this.typeOfProduct = typeOfProduct;
            this.businessTaxNumber = businessTaxNumber;
            this.amount = amount;
            this.taxAmount = taxAmount;
            this.date = date;
            this.businessAddress = businessAddress;
            this.customerAddress = customerAddress;
            this.termOfPayment = termOfPayment;
        }

        public Invoice(){
        }

    }
