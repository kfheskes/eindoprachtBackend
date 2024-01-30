package nl.backend.eindoprdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindoprdracht.dtos.file.FileDto;
import nl.backend.eindoprdracht.services.FileService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ResponseEntity<String> singleFileUpload(@RequestParam MultipartFile file, @Valid @RequestParam() String description, Long order_id) throws IOException {
        FileDto fileDto = fileService.addFile(file, description, order_id);
        return ResponseEntity.ok("succes upload file " + fileDto.getFilename());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getFileById(@PathVariable Long id) {
        byte[] fileByte = fileService.getFileById(id);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF ).body(fileByte);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

}