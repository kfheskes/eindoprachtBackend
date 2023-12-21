package nl.backend.eindoprdracht.dtos.employeeaccount;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EmployeeAccountOutputDto {

    public long id;
public String fName;
    public String mName;
    public String lName;
    public Date dob;
public String address;
    public String zipcode;
    public String pNumber;
public Double contractH;
    public Date startContract;
}
