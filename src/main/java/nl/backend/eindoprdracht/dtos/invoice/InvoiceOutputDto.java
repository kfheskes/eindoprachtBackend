package nl.backend.eindoprdracht.dtos.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.models.CustomerAccount;
import nl.backend.eindoprdracht.utils.TermOfPayment;
import nl.backend.eindoprdracht.utils.TypeOfWork;

import java.time.LocalDate;
import java.util.Set;

public class InvoiceOutputDto {

    public Long id;
    public TypeOfWork typeOfWork;
    public Double price;
    public String typeOfProduct;
    public String businessTaxNumber;
    public Integer amount;
    public Long taxAmount;

    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate date;
    public String businessAddress;
    public String customerAddress;
    public TermOfPayment termOfPayment;


    private CustomerAccountOutputDto customers;

    public void setCustomerAccountOutputDto(CustomerAccountOutputDto customerAccountOutputDto) {
        this.customers = customerAccountOutputDto;
    }

}
