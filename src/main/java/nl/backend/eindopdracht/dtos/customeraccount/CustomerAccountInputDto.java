package nl.backend.eindopdracht.dtos.customeraccount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAccountInputDto {

    public String companyName;
    public String contract;
    public Double balans;
}
