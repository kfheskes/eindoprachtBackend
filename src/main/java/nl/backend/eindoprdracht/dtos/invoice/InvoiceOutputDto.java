package nl.backend.eindoprdracht.dtos.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.models.CustomerAccount;

import java.time.LocalDate;
import java.util.Set;

public class InvoiceOutputDto {

    public Long id;
    public String typeOfWork;
    public Double price;
    public String typeOfProduct;
    public String businessTaxNumber;
    public Integer amount;
    public Long taxAmount;

    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate date;
    public String businessAddress;
    public String customerAddress;
    public String termOfPayment;


    private CustomerAccountOutputDto customers;

    public void setCustomerAccountOutputDto(CustomerAccountOutputDto customerAccountOutputDto) {
        this.customers = customerAccountOutputDto;
    }

}
