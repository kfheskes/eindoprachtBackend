package nl.backend.eindoprdracht.dtos.employeeaccount;

import nl.backend.eindoprdracht.dtos.workschedule.WorkScheduleOutputDto;
import nl.backend.eindoprdracht.models.WorkSchedule;

import java.util.Date;


public class EmployeeAccountOutputDto {

    public long id;

    public Double contractH;
    public Date startContract;


    public void setWorkScheduleOutputDto(WorkScheduleOutputDto workScheduleDto) {
        this.workScheduleDto = workScheduleDto;
    }

    public WorkScheduleOutputDto workScheduleDto;


}
