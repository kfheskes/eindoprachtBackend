package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountInputDto;
import nl.backend.eindoprdracht.dtos.customeraccount.CustomerAccountOutputDto;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountInputDto;
import nl.backend.eindoprdracht.dtos.employeeaccount.EmployeeAccountOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.CustomerAccount;
import nl.backend.eindoprdracht.models.EmployeeAccount;
import nl.backend.eindoprdracht.repositories.EmployeeAccountRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeAccountService {

    private EmployeeAccountRepository employeeAccountRepository;

    public EmployeeAccountService(EmployeeAccountRepository employeeAccountRepository) {
        this.employeeAccountRepository = employeeAccountRepository;
    }

    public EmployeeAccount dtoTransferToEmployeeAccount(EmployeeAccountInputDto employeeAccountInputDto) {
        EmployeeAccount employeeAccount = new EmployeeAccount();

        employeeAccount.setFName( employeeAccountInputDto.getFName());
        employeeAccount.setMName(employeeAccountInputDto.getMName());
        employeeAccount.setLName(employeeAccountInputDto.getLName());
        employeeAccount.setDob(employeeAccountInputDto.getDob());
        employeeAccount.setAddress(employeeAccountInputDto.getAddress());
        employeeAccount.setZipcode(employeeAccountInputDto.getZipcode());
        employeeAccount.setPNumber(employeeAccountInputDto.getPNumber());
        employeeAccount.setContractH(employeeAccountInputDto.getContractH());
        employeeAccount.setStartContract(employeeAccountInputDto.getStartContract());
        return employeeAccount;
    }

//TODO check of onderstaande juist werkt anders aanpassen zie customerAccountService navragen bij Rowan
    public EmployeeAccountOutputDto employeeAccountTransferToDto(EmployeeAccount employeeAccount) {
        EmployeeAccountOutputDto employeeAccountOutputDto = new EmployeeAccountOutputDto();
        employeeAccountOutputDto.id = (employeeAccount.getId());
        employeeAccountOutputDto.fName = employeeAccount.getFName();
        employeeAccountOutputDto.mName = (employeeAccount.getMName());
        employeeAccountOutputDto.lName = (employeeAccount.getLName());
        employeeAccountOutputDto.dob = (employeeAccount.getDob());
        employeeAccountOutputDto.address = (employeeAccount.getAddress());
        employeeAccountOutputDto.zipcode = (employeeAccount.getZipcode());
        employeeAccountOutputDto.pNumber = (employeeAccount.getPNumber());
        employeeAccountOutputDto.contractH = (employeeAccount.getContractH());
        employeeAccountOutputDto.startContract = (employeeAccount.getStartContract());

        return employeeAccountOutputDto;
    }

    public EmployeeAccountOutputDto createEmployeeAccount (EmployeeAccountInputDto employeeAccount ){
        EmployeeAccount employeeAccountInputDto = dtoTransferToEmployeeAccount(employeeAccount);
        employeeAccountRepository.save(employeeAccountInputDto);
        return employeeAccountTransferToDto(employeeAccountInputDto);
    }

    public EmployeeAccountOutputDto getEmployeeAccountId(long id) {
        Optional<EmployeeAccount> employeeAccountId = employeeAccountRepository.findById(id);

        if (employeeAccountId.isPresent()) {
            EmployeeAccount ea = employeeAccountId.get();
            EmployeeAccountOutputDto dto = employeeAccountTransferToDto(ea);
            return dto;
        } else {
            throw new RecordNotFoundException("No customerAccount id found" + id);
        }
    }

    public List<EmployeeAccountOutputDto> getAllEmployees(){
        List<EmployeeAccount> employeeAccounts = employeeAccountRepository.findAll();
        List<EmployeeAccountOutputDto> employeeAccountOutputDtoList = new ArrayList<>();
        for (EmployeeAccount employeeAccount : employeeAccounts) {
            employeeAccountOutputDtoList.add(employeeAccountTransferToDto(employeeAccount));
        }
        return employeeAccountOutputDtoList;
    }

    public EmployeeAccountOutputDto updateEmployeeAccount(long id, EmployeeAccountInputDto employeeAccount) {
        Optional<EmployeeAccount> getEmployeeAccount = employeeAccountRepository.findById(id);
        if (getEmployeeAccount.isEmpty()) {
            throw new RecordNotFoundException("No customerAccount found by id");
        } else {
            EmployeeAccount changeEmployeeAccount1 = getEmployeeAccount.get();

            if (employeeAccount.getFName() != null) {
               changeEmployeeAccount1.setFName(employeeAccount.getFName());
            }
            if (employeeAccount.getMName() != null) {
                changeEmployeeAccount1.setMName(employeeAccount.getMName());
            }
            if (employeeAccount.getLName() != null) {
                changeEmployeeAccount1.setLName(employeeAccount.getLName());
            }
            if (employeeAccount.getDob() != null) {
                changeEmployeeAccount1.setDob(employeeAccount.getDob());
            }
            if (employeeAccount.getAddress() != null) {
                changeEmployeeAccount1.setAddress(employeeAccount.getAddress());
            }
            if (employeeAccount.getZipcode() != null) {
                changeEmployeeAccount1.setZipcode(employeeAccount.getZipcode());
            }
            if (employeeAccount.getPNumber() != null) {
                changeEmployeeAccount1.setPNumber(employeeAccount.getPNumber());
            }
            if (employeeAccount.getContractH() != null) {
                changeEmployeeAccount1.setContractH(employeeAccount.getContractH());
            }
            if (employeeAccount.getStartContract() != null) {
                changeEmployeeAccount1.setStartContract(employeeAccount.getStartContract());
            }
            EmployeeAccount returnEmployeeAccount = employeeAccountRepository.save(changeEmployeeAccount1);
            return employeeAccountTransferToDto(returnEmployeeAccount);
        }

    }

    public void deleteEmployeeAccount(long id) {
        employeeAccountRepository.deleteById(id);
    }

}
