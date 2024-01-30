package nl.backend.eindoprdracht.dtos.employeeaccount;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
public class EmployeeAccountInputDto {


    public Double contractH;
    @NotBlank(message = "start date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    public LocalDate startContract;
}
