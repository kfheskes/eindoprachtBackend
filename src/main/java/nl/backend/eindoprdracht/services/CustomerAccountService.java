package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountInputDto;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.CustomerAccount;
import nl.backend.eindoprdracht.repositories.CustomerAccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


// TODO zorg overal voor ctr alt L
@Service
public class CustomerAccountService {

    private CustomerAccountRepository customerAccountRepository;

    public CustomerAccountService(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    public CustomerAccount dtoTransferToCustomerAccount(CustomerAccountInputDto customerAccountInputDto) {
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

    public CustomerAccountOutputDto customerAccountTransferCustomerAccountOutputDto(CustomerAccount customerAccount) {
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

    public CustomerAccountOutputDto getCustomerAccountId(long id) {
        Optional<CustomerAccount> customerAccountId = customerAccountRepository.findById(id);

        if (customerAccountId.isPresent()) {
            CustomerAccount ca = customerAccountId.get();
            CustomerAccountOutputDto dto = customerAccountTransferCustomerAccountOutputDto(ca);
            return dto;
        } else {
            throw new RecordNotFoundException("No customerAccount id found" + id);
        }
    }

    public List<CustomerAccountOutputDto> getAllCustomer() {
        List<CustomerAccount> customerAccounts = customerAccountRepository.findAll();
        List<CustomerAccountOutputDto> customerAccountOutputDtoList = new ArrayList<>();
        for (CustomerAccount customerAccount : customerAccounts) {
            customerAccountOutputDtoList.add(customerAccountTransferCustomerAccountOutputDto(customerAccount));
        }
        return customerAccountOutputDtoList;
    }

    public void deleteCustomerAccount(long id) {
        customerAccountRepository.deleteById(id);
    }

    public CustomerAccountOutputDto updateCustomerAccount(long id, CustomerAccount customerAccount) {
        Optional<CustomerAccount> getCustomerAccount = customerAccountRepository.findById(id);
        if (getCustomerAccount.isEmpty()) {
            throw new RecordNotFoundException("No customerAccount found by id");
        } else {
            CustomerAccount changeCustomerAccount1 = getCustomerAccount.get();
            changeCustomerAccount1.setFName(customerAccount.getFName());
            changeCustomerAccount1.setMName(customerAccount.getMName());
            changeCustomerAccount1.setLName(customerAccount.getLName());
            changeCustomerAccount1.setAddress(customerAccount.getAddress());
            changeCustomerAccount1.setZipcode(customerAccount.getZipcode());
            changeCustomerAccount1.setPNumber(customerAccount.getPNumber());
            changeCustomerAccount1.setCompanyName(customerAccount.getCompanyName());
            changeCustomerAccount1.setContract(customerAccount.getContract());
            changeCustomerAccount1.setBalans(customerAccount.getBalans());
            CustomerAccount returnCustomerAccount = customerAccountRepository.save(changeCustomerAccount1);
            return customerAccountTransferCustomerAccountOutputDto(returnCustomerAccount);
        }

    }

}


