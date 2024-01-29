package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository <File, Long> {
    List<File> findByOrder_Id(Long orderId);

}
