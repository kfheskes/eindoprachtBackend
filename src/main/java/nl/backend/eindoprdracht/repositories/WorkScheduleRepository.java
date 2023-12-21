package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkScheduleRepository extends JpaRepository <WorkSchedule, Long> {
}
