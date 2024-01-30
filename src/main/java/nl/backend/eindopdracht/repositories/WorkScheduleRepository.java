package nl.backend.eindopdracht.repositories;

import nl.backend.eindopdracht.models.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkScheduleRepository extends JpaRepository <WorkSchedule, Long> {
}
