package nl.backend.eindoprdracht.dtos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindoprdracht.dtos.invoice.InvoiceOutputDto;
import nl.backend.eindoprdracht.dtos.manageraccount.ManagerAccountOutputDto;
import nl.backend.eindoprdracht.utils.TypeOfWork;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class OrderOutputDto {

    private Long Id;
    private TypeOfWork typeOfWork;
    private Integer amount;
    private Double price;
    private String productName;

    private String customerId;
    private String status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateCreated;
    private LocalTime time;
    private String workAddress;
    private String workZipcode;

    private InvoiceOutputDto invoiceOutputDto;


    public void setInvoiceOutputDto(InvoiceOutputDto invoiceOutputDto) {
        this.invoiceOutputDto = invoiceOutputDto;
    }

    private Set<EmployeeAccountOutputDto> employees;

    private Set<ManagerAccountOutputDto> managers;

    private CustomerAccountOutputDto customerAccountOutputDto;

    public void setCustomerAccountOutputDto(CustomerAccountOutputDto customerAccountOutputDto) {
        this.customerAccountOutputDto = customerAccountOutputDto;
    }


    public OrderOutputDto() {
    }


}
