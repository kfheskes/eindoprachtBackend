package nl.backend.eindoprdracht.dtos.file;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindoprdracht.models.Order;

@Getter
@Setter
public class FileDto {
    public Long id;

    public String contentType;
    public String filename;

    public byte[] data;

    @NotBlank
    public String description;

    public Order order;



}
