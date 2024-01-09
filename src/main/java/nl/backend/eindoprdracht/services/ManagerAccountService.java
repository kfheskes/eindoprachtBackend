package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.manageraccount.ManagerAccountInputDto;
import nl.backend.eindoprdracht.dtos.manageraccount.ManagerAccountOutputDto;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.ManagerAccount;
import nl.backend.eindoprdracht.models.WorkSchedule;
import nl.backend.eindoprdracht.repositories.ManagerAccountRepository;
import nl.backend.eindoprdracht.repositories.WorkScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerAccountService {

    private final ManagerAccountRepository managerAccountRepository;

    private final WorkScheduleRepository workScheduleRepository;
    private final WorkScheduleService workScheduleService;

    public ManagerAccountService(ManagerAccountRepository managerAccountRepository, WorkScheduleRepository workScheduleRepository, WorkScheduleService workScheduleService) {
        this.managerAccountRepository = managerAccountRepository;
        this.workScheduleRepository = workScheduleRepository;
        this.workScheduleService = workScheduleService;
    }

    public ManagerAccount dtoTransferToManagerAccount(ManagerAccountInputDto dto) {
        ManagerAccount managerAccount = new ManagerAccount();

        managerAccount.setFName(dto.getFName());
        managerAccount.setMName(dto.getMName());
        managerAccount.setLName(dto.getLName());
        managerAccount.setDob(dto.getDob());
        managerAccount.setAddress(dto.getAddress());
        managerAccount.setZipcode(dto.getZipcode());
        managerAccount.setPNumber(dto.getPNumber());
        managerAccount.setResponsibilities(dto.getResponsibilities());

        return managerAccount;
    }

    public ManagerAccountOutputDto managerAccountTransferToDto(ManagerAccount managerAccount) {
        ManagerAccountOutputDto dto = new ManagerAccountOutputDto();

        dto.id = (managerAccount.getId());
        dto.fName =(managerAccount.getFName());
        dto.mName =(managerAccount.getMName());
        dto.lName =(managerAccount.getLName());
        dto.dob =(managerAccount.getDob());
        dto.address = (managerAccount.getAddress());
        dto.zipcode = (managerAccount.getZipcode());
        dto.pNumber = (managerAccount.getPNumber());
        dto.responsibilities = (managerAccount.getResponsibilities());

        if (managerAccount.getWorkSchedule() != null) {
            dto.setManagerAccountOutputDto(workScheduleService.workScheduleTransferToDto(managerAccount.getWorkSchedule()));
        }


        return dto;
    }

    public ManagerAccountOutputDto createManagerAccount(ManagerAccountInputDto dto) {
        ManagerAccount managerAccount = dtoTransferToManagerAccount(dto);
        managerAccountRepository.save(managerAccount);
        return managerAccountTransferToDto(managerAccount);
    }

    public ManagerAccountOutputDto getManagerAccountId(long id) {
        Optional<ManagerAccount> managerAccount = managerAccountRepository.findById(id);
        if (managerAccount.isPresent()) {
            return managerAccountTransferToDto(managerAccount.get());
        } else {
            throw new RecordNotFoundException("No manager account found with id: " + id);
        }
    }

    public List<ManagerAccountOutputDto> getAllManagers() {
        List<ManagerAccount> managerAccounts = managerAccountRepository.findAll();
        List<ManagerAccountOutputDto> dtoList = new ArrayList<>();
        for (ManagerAccount account : managerAccounts) {
            dtoList.add(managerAccountTransferToDto(account));
        }
        return dtoList;
    }

    public ManagerAccountOutputDto updateManagerAccount(long id, ManagerAccountInputDto dto) {
        Optional<ManagerAccount> getManagerAccount = managerAccountRepository.findById(id);
        if (getManagerAccount.isEmpty()){
            throw new RecordNotFoundException("No Manager account found by id" + id);
        } else  {
            ManagerAccount accountToUpdate = getManagerAccount.get();
            if(dto.getFName() != null) {
                accountToUpdate.setFName(dto.getFName());
            }
            if(dto.getMName() != null) {
                accountToUpdate.setMName(dto.getMName());
            }
            if(dto.getLName() != null) {
                accountToUpdate.setLName(dto.getLName());
            }
            if (dto.getDob() != null) {
                accountToUpdate.setDob(dto.getDob());
            }
            if (dto.getAddress() != null) {
                accountToUpdate.setAddress(dto.getAddress());
            }
            if (dto.getZipcode() != null) {
                accountToUpdate.setZipcode(dto.getZipcode());
            }
            if (dto.getPNumber() != null) {
                accountToUpdate.setPNumber(dto.getPNumber());
            }
            if (dto.getResponsibilities() != null) {
                accountToUpdate.setResponsibilities(dto.getResponsibilities());
            }
            ManagerAccount updatedAccount = managerAccountRepository.save(accountToUpdate);
            return managerAccountTransferToDto(updatedAccount);
        }

    }

    public void assignManagerToWorkSchedule (long managerAccountId, long workScheduleId){
        Optional<ManagerAccount> optionalManagerAccount = managerAccountRepository.findById(managerAccountId);
        Optional<WorkSchedule> optionalWorkSchedule = workScheduleRepository.findById(workScheduleId);

        if (optionalManagerAccount.isPresent() && optionalWorkSchedule.isPresent()) {
            ManagerAccount manager = optionalManagerAccount.get();
            WorkSchedule workSchedule = optionalWorkSchedule.get();
            manager.setWorkSchedule(workSchedule);
            managerAccountRepository.save(manager);
        } else {
            throw new RecordNotFoundException("No managerAccount find with work schedule");
        }

    }

    public void deleteManagerAccount(long id) {
        if (!managerAccountRepository.existsById(id)) {
            throw new RecordNotFoundException("No manager account found with id: " + id);
        }
        managerAccountRepository.deleteById(id);
    }

}
