package nl.backend.eindoprdracht.dtos.customeraccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAccountOutputDto {

    public long id;
    public String fName;
    public String mName;
    public String lName;
    public String address;
    public String zipcode;
    public String pNumber;
    public String companyName;
    public String contract;
    public Double balans;
}
