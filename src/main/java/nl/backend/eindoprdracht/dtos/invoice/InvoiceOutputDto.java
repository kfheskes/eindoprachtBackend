package nl.backend.eindoprdracht.dtos.invoice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class InvoiceOutputDto {

    public Long Id;
    public String typeOfWork;
    public Double price;
    public String typeOfProduct;
    public String businessTaxNumber;
    public int amount;
    public long taxAmount;
    public LocalDate date;
    public String businessAddress;
    public String customerAddress;
    public String termOfPayment;
}
