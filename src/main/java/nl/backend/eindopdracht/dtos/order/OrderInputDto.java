package nl.backend.eindopdracht.dtos.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindopdracht.utils.TypeOfWork;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OrderInputDto {
    @NotNull(message = "Type of work is required")
    public TypeOfWork typeOfWork;
    public Integer amount;
    public Double price;
    public String productName;
    @NotBlank
    public String status;
    @FutureOrPresent
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate dateCreated;
    public LocalTime time;
    @NotBlank
    public String workAddress;
    @NotBlank
    public String workZipcode;
}
