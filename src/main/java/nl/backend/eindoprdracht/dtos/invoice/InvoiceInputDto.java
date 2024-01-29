package nl.backend.eindoprdracht.dtos.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.utils.TypeOfWork;
import java.time.LocalDate;



@Getter
@Setter
public class InvoiceInputDto {

    @NotBlank(message = "Type of work is required")
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
    public String termOfPayment;





}
