package nl.backend.eindoprdracht.dtos.file;

import jakarta.validation.constraints.NotBlank;
import nl.backend.eindoprdracht.models.Invoice;

public class FileDto {
    public Long id;

    public String filename;
    @NotBlank
    public byte[] data;
    public String mimeType;
    @NotBlank
    public String description;
    public Invoice invoice;



}
