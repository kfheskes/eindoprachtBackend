package nl.backend.eindoprdracht.dtos.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class OrderInputDto {
    public String typeOfWork;
    public Integer amount;
    public Double price;
    public Integer productId;
    public String productName;
    public String customerName;
    public String status;
    public LocalDate dateCreated;
    public LocalTime time;
    public String workAddress;
    public String workZipcode;
}
