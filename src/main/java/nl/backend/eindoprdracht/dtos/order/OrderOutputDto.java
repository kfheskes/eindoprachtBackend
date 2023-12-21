package nl.backend.eindoprdracht.dtos.order;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
public class OrderOutputDto {

    public Long Id;
    public String typeOfWork;
    public int amount;
    public Double price;
    public int productId;
    public String productName;
    public String customerName;
    public String status;
    public LocalDate dateCreated;
    public LocalTime time;
    public String workAddress;
    public String workZipcode;
}
