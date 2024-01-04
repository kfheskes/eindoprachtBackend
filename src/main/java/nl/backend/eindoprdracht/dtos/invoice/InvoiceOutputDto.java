package nl.backend.eindoprdracht.dtos.invoice;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class InvoiceOutputDto {

    public Long id;
    public String typeOfWork;
    public Double price;
    public String typeOfProduct;
    public String businessTaxNumber;
    public Integer amount;
    public Long taxAmount;
    public LocalDate date;
    public String businessAddress;
    public String customerAddress;
    public String termOfPayment;
}
