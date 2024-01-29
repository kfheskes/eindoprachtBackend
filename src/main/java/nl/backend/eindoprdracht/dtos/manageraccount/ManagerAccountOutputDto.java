package nl.backend.eindoprdracht.dtos.manageraccount;

import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.dtos.workschedule.WorkScheduleOutputDto;

import java.util.Date;

public class ManagerAccountOutputDto {

    public Long id;

    public String responsibilities;

    public void setManagerAccountOutputDto(WorkScheduleOutputDto workScheduleDto) {
        this.workScheduleDto = workScheduleDto;
    }

    public WorkScheduleOutputDto workScheduleDto;
}
