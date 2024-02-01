package nl.backend.eindopdracht.dtos.invoice;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindopdracht.utils.TypeOfWork;

import java.time.LocalDate;


@Getter
@Setter
public class InvoiceInputDto {

    @NotBlank(message = "Type of work is required")

    public TypeOfWork typeOfWork;
    @Positive(message = "Price can't by negatieve")
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
