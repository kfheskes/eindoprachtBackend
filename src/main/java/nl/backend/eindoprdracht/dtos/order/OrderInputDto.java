package nl.backend.eindoprdracht.dtos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.utils.TypeOfWork;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OrderInputDto {
    @NotBlank(message = "Type of work is required")
    public TypeOfWork typeOfWork;
    public Integer amount;
    public Double price;
    public String productName;
    public String customerId;
    public String status;
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate dateCreated;
    public LocalTime time;
    public String workAddress;
    public String workZipcode;
}
