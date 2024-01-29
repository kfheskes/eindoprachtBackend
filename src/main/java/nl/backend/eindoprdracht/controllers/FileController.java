package nl.backend.eindoprdracht.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        // Implementeer logica om bestand op te slaan
        return "Bestand succesvol geüpload: " + file.getOriginalFilename();
    }
}

