package nl.backend.eindoprdracht.dtos.file;

import jakarta.validation.constraints.NotBlank;
import nl.backend.eindoprdracht.models.Order;

public class FileDto {
    public Long id;

    public String filename;
    @NotBlank
    public byte[] data;
    public String mimeType;
    @NotBlank
    public String description;

    public Order order;



}
