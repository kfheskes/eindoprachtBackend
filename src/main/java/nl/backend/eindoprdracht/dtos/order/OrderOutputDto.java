package nl.backend.eindoprdracht.dtos.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

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
}
