package nl.backend.eindoprdracht.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter

@Entity
@Table(name = "work_schedule")
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;

    private LocalDate date;

    private LocalTime time;

    private LocalDateTime available;

    private LocalDateTime absence;

    private LocalDate sick;

    private String managerAvailable;

    @OneToMany(mappedBy = "workSchedule")
    private Set<EmployeeAccount> employees = new HashSet<>();

    @OneToMany(mappedBy =  "workSchedule")
    private Set<ManagerAccount> managers = new HashSet<>();

    public WorkSchedule(long id, LocalDate date, LocalTime time, LocalDateTime available, LocalDateTime absence, LocalDate sick, String managerAvailable) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.available = available;
        this.absence = absence;
        this.sick = sick;
        this.managerAvailable = managerAvailable;
    }

    public WorkSchedule(){

    }
}
