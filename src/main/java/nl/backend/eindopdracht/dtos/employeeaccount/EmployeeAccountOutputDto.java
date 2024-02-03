package nl.backend.eindopdracht.dtos.employeeaccount;

import nl.backend.eindopdracht.dtos.workschedule.WorkScheduleOutputDto;

import java.time.LocalDate;


public class EmployeeAccountOutputDto {

    public long id;
    public Double contractH;
    public LocalDate startContract;


    public void setWorkScheduleOutputDto(WorkScheduleOutputDto workScheduleDto) {
        this.workScheduleDto = workScheduleDto;
    }

    public WorkScheduleOutputDto workScheduleDto;


}
