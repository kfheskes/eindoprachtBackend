package nl.backend.eindopdracht.services;

import nl.backend.eindopdracht.dtos.customeraccount.CustomerAccountInputDto;
import nl.backend.eindopdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindopdracht.exceptions.RecordNotFoundException;
import nl.backend.eindopdracht.models.CustomerAccount;
import nl.backend.eindopdracht.repositories.CustomerAccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    public CustomerAccountService(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    public CustomerAccount dtoTransferToCustomerAccount(CustomerAccountInputDto customerAccountInputDto) {
        CustomerAccount customerAccount = new CustomerAccount();

        customerAccount.setCompanyName(customerAccountInputDto.getCompanyName());
        customerAccount.setContract(customerAccountInputDto.getContract());
        customerAccount.setBalans(customerAccountInputDto.getBalans());
        return customerAccount;
    }

    public CustomerAccountOutputDto customerAccountTransferCustomerAccountOutputDto(CustomerAccount customerAccount) {
        CustomerAccountOutputDto customerAccountOutputDto = new CustomerAccountOutputDto();
        customerAccountOutputDto.id = customerAccount.getId();

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

    public List<CustomerAccountOutputDto> getAllCustomers() {
        List<CustomerAccount> customerAccounts = customerAccountRepository.findAll();
        List<CustomerAccountOutputDto> customerAccountOutputDtoList = new ArrayList<>();
        for (CustomerAccount customerAccount : customerAccounts) {
            customerAccountOutputDtoList.add(customerAccountTransferCustomerAccountOutputDto(customerAccount));
        }
        return customerAccountOutputDtoList;
    }

    public CustomerAccountOutputDto updateCustomerAccount(long id, CustomerAccountInputDto customerAccount) {
        Optional<CustomerAccount> getCustomerAccount = customerAccountRepository.findById(id);
        if (getCustomerAccount.isEmpty()) {
            throw new RecordNotFoundException("No customerAccount found by id");
        } else {
            CustomerAccount changeCustomerAccount1 = getCustomerAccount.get();

            if (customerAccount.getCompanyName() != null) {
                changeCustomerAccount1.setCompanyName(customerAccount.getCompanyName());
            }
            if (customerAccount.getContract() != null) {
                changeCustomerAccount1.setContract(customerAccount.getContract());
            }
            if (customerAccount.getBalans() != null) {
                changeCustomerAccount1.setBalans(customerAccount.getBalans());
            }
            CustomerAccount returnCustomerAccount = customerAccountRepository.save(changeCustomerAccount1);
            return customerAccountTransferCustomerAccountOutputDto(returnCustomerAccount);
        }

    }
    public void deleteCustomerAccount(long id) {
        customerAccountRepository.deleteById(id);
    }





}


