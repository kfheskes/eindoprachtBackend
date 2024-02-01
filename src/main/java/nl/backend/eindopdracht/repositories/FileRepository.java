package nl.backend.eindopdracht.repositories;

import nl.backend.eindopdracht.models.File;
import org.springframework.data.jpa.repository.JpaRepository;


public interface FileRepository extends JpaRepository <File, Long> {


}
