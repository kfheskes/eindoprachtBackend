package nl.backend.eindopdracht.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter

@Entity
@Table(name = "work_schedule")
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    private LocalTime time;

    private LocalDateTime available;

    private LocalDateTime absence;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate sick;

    private String managerAvailable;

    @OneToMany(mappedBy = "workSchedule",orphanRemoval = true)
    private Set<EmployeeAccount> employees = new HashSet<>();

    @OneToMany(mappedBy =  "workSchedule",orphanRemoval = true)
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
