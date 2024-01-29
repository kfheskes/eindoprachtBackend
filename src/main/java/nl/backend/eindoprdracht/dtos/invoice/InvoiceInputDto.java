package nl.backend.eindoprdracht.dtos.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Getter
@Setter
public class InvoiceInputDto {

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
