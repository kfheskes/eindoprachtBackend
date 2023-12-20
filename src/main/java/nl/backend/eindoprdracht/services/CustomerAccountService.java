package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountInputDto;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.models.CustomerAccount;
import nl.backend.eindoprdracht.repositories.CustomerAccountRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountService {

    private CustomerAccountRepository customerAccountRepository;

    public CustomerAccountService(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    public CustomerAccount dtoTransferToCustomerAccount (CustomerAccountInputDto customerAccountInputDto){
        CustomerAccount customerAccount = new CustomerAccount();

        customerAccount.setFName(customerAccountInputDto.getFName());
        customerAccount.setMName(customerAccountInputDto.getMName());
        customerAccount.setLName(customerAccountInputDto.getLName());
        customerAccount.setAddress(customerAccountInputDto.getAddress());
        customerAccount.setZipcode(customerAccountInputDto.getZipcode());
        customerAccount.setPNumber(customerAccountInputDto.getPNumber());
        customerAccount.setCompanyName(customerAccountInputDto.getCompanyName());
        customerAccount.setContract(customerAccountInputDto.getContract());
        customerAccount.setBalans(customerAccountInputDto.getBalans());
    return customerAccount;
    }

public CustomerAccountOutputDto customerAccountTransferCustomerAccountOutputDto(CustomerAccount customerAccount){
        CustomerAccountOutputDto customerAccountOutputDto = new CustomerAccountOutputDto();
        customerAccountOutputDto.id = customerAccount.getId();
        customerAccountOutputDto.fName = customerAccount.getFName();
        customerAccountOutputDto.mName = customerAccount.getMName();
        customerAccountOutputDto.lName = customerAccount.getLName();
        customerAccountOutputDto.address = customerAccount.getAddress();
        customerAccountOutputDto.zipcode = customerAccount.getZipcode();
        customerAccountOutputDto.pNumber = customerAccount.getPNumber();
        customerAccountOutputDto.companyName = customerAccount.getCompanyName();
        customerAccountOutputDto.contract = customerAccount.getContract();
        customerAccountOutputDto.balans = customerAccount.getBalans();
        return customerAccountOutputDto;
}

public CustomerAccountOutputDto createCustomerAccount(CustomerAccountInputDto customerAccount) {
        CustomerAccount customerAccountInputDto = dtoTransferToCustomerAccount(customerAccount);
        customerAccountRepository.save(customerAccountInputDto);
        return customerAccountTransferCustomerAccountOutputDto(customerAccountInputDto);
}


}
