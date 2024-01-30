package nl.backend.eindopdracht.dtos.manageraccount;

import nl.backend.eindopdracht.dtos.workschedule.WorkScheduleOutputDto;

public class ManagerAccountOutputDto {

    public Long id;

    public String responsibilities;

    public void setManagerAccountOutputDto(WorkScheduleOutputDto workScheduleDto) {
        this.workScheduleDto = workScheduleDto;
    }

    public WorkScheduleOutputDto workScheduleDto;
}
