package nl.backend.eindopdracht.dtos.employeeaccount;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class EmployeeAccountInputDto {

    public Double contractH;
    @NotBlank(message = "start date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Past
    public LocalDate startContract;
}
