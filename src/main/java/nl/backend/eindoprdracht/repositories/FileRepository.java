package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository <File, Long> {


    List<File> findByTask_Id(Long taskId);
}
