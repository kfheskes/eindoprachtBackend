package nl.backend.eindopdracht.dtos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindopdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindopdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindopdracht.dtos.invoice.InvoiceOutputDto;
import nl.backend.eindopdracht.dtos.manageraccount.ManagerAccountOutputDto;
import nl.backend.eindopdracht.utils.TypeOfWork;

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
