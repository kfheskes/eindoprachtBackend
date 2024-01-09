package nl.backend.eindoprdracht.dtos.order;

import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindoprdracht.dtos.invoice.InvoiceOutputDto;
import nl.backend.eindoprdracht.dtos.manageraccount.ManagerAccountOutputDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
public class OrderOutputDto {

    private Long Id;
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
