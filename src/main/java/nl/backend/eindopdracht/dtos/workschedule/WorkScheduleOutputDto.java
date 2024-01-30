package nl.backend.eindopdracht.dtos.workschedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class WorkScheduleOutputDto {

    public long id;
    public LocalDate date;
    public LocalTime time;
    public LocalDateTime available;
    public LocalDateTime absence;
    public LocalDate sick;
    public String managerAvailable;
}
