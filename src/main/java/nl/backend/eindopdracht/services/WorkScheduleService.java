package nl.backend.eindopdracht.services;

import nl.backend.eindopdracht.dtos.workschedule.WorkScheduleInputDto;
import nl.backend.eindopdracht.dtos.workschedule.WorkScheduleOutputDto;
import nl.backend.eindopdracht.exceptions.RecordNotFoundException;
import nl.backend.eindopdracht.models.WorkSchedule;
import nl.backend.eindopdracht.repositories.WorkScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;

    public WorkScheduleService(WorkScheduleRepository workScheduleRepository) {
        this.workScheduleRepository = workScheduleRepository;
    }

    public WorkSchedule dtoTransferToWorkSchedule (WorkScheduleInputDto inputDto) {
        WorkSchedule workSchedule = new WorkSchedule();

        workSchedule.setDate(inputDto.getDate());
        workSchedule.setTime(inputDto.getTime());
        workSchedule.setAvailable(inputDto.getAvailable());
        workSchedule.setAbsence(inputDto.getAbsence());
        workSchedule.setSick(inputDto.getSick());
        workSchedule.setManagerAvailable(inputDto.getManagerAvailable());
        return workSchedule;
    }

public WorkScheduleOutputDto workScheduleTransferToDto (WorkSchedule workSchedule){
        WorkScheduleOutputDto outputDto = new WorkScheduleOutputDto();

        outputDto.id = workSchedule.getId();
        outputDto.date = workSchedule.getDate();
        outputDto.time = workSchedule.getTime();
        outputDto.available = workSchedule.getAvailable();
        outputDto.absence = workSchedule.getAbsence();
        outputDto.sick = workSchedule.getSick();
        outputDto.managerAvailable = workSchedule.getManagerAvailable();
        return outputDto;
}

public WorkScheduleOutputDto createWorkSchedule (WorkScheduleInputDto inputDto){
        WorkSchedule workSchedule = dtoTransferToWorkSchedule(inputDto);
        workScheduleRepository.save(workSchedule);
        WorkScheduleOutputDto workScheduleOutputDto = workScheduleTransferToDto(workSchedule);
        return  workScheduleOutputDto;
}

public WorkScheduleOutputDto getWorkScheduleId (long id){
    Optional<WorkSchedule> workScheduleId = workScheduleRepository.findById(id);

    if (workScheduleId.isPresent()){
        WorkSchedule ws = workScheduleId.get();
        WorkScheduleOutputDto dto = workScheduleTransferToDto(ws);
        return dto;
    } else {
        throw new RecordNotFoundException("No work schedule id found" + id);
    }
}

public List<WorkScheduleOutputDto> getAllWorkSchedules(){
        List<WorkSchedule> workScheduleList = workScheduleRepository.findAll();
        List<WorkScheduleOutputDto> workScheduleOutputDtoList = new ArrayList<>();
    for (WorkSchedule workSchedule : workScheduleList ) {
        workScheduleOutputDtoList.add(workScheduleTransferToDto(workSchedule));
    }
    return workScheduleOutputDtoList;
}

public WorkScheduleOutputDto updateWorkSchedule (long id, WorkScheduleInputDto inputDto){
        Optional<WorkSchedule> updateWS = workScheduleRepository.findById(id);
        if (updateWS.isEmpty()){
            throw new RecordNotFoundException("no work schedule found with id" + id);
        } else {
            WorkSchedule changeWorkSchedule = updateWS.get();
            if (inputDto.getDate() != null) {
                changeWorkSchedule.setDate(inputDto.getDate());
            }
            if (inputDto.getTime() != null) {
                changeWorkSchedule.setTime(inputDto.getTime());
            }
            if (inputDto.getAvailable() != null) {
                changeWorkSchedule.setAvailable(inputDto.getAvailable());
            }
            if (inputDto.getAbsence() != null) {
            changeWorkSchedule.setAbsence(inputDto.getAbsence());
            }
            if (inputDto.getSick() != null) {
                changeWorkSchedule.setSick(inputDto.getSick());
            }
            if (inputDto.getManagerAvailable() != null) {
                changeWorkSchedule.setManagerAvailable(inputDto.getManagerAvailable());
            }
            WorkSchedule returnWorkSchedule = workScheduleRepository.save(changeWorkSchedule);
            return workScheduleTransferToDto(returnWorkSchedule);
        }

}

public void deleteWorkSchedule(long id) {
        workScheduleRepository.deleteById(id);
}


}
