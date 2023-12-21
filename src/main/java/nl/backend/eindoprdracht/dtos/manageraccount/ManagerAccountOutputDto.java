package nl.backend.eindoprdracht.dtos.manageraccount;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class ManagerAccountOutputDto {

    public long id;
    public String fName;
    public String mName;
    public String lName;
    public Date dob;
    public String address;
    public String zipcode;
    public String pNumber;
public String responsibilities;
}
