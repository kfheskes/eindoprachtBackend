package nl.backend.eindopdracht.dtos.file;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nl.backend.eindopdracht.models.Order;

@Getter
@Setter
public class FileDto {
    public Long id;

    public String contentType;
    public String filename;

    public byte[] data;

    @NotBlank(message = "can't be empty")
    public String description;

    public Order order;



}
