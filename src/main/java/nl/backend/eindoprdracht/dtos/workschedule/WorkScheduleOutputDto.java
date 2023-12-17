package nl.backend.eindoprdracht.dtos.workschedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@Getter
@Setter
public class WorkScheduleOutputDto {

    public long id;
    public LocalDate date;
    public LocalTime time;
    public LocalDateTime available;
    public LocalDateTime absence;
    public LocalDate sick;
    public String managerAvailable;
}
