package nl.backend.eindoprdracht.controllers;

import jakarta.validation.Valid;
import nl.backend.eindoprdracht.dtos.file.FileDto;
import nl.backend.eindoprdracht.services.FileService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping
    public ResponseEntity<List<FileDto>> getAllFiles() {
        List<FileDto> fileDtos = fileService.getAllFiles();
        return ResponseEntity.ok(fileDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> getFileById(@PathVariable Long id) {
        FileDto fileDto = fileService.getFileById(id);
        return ResponseEntity.ok(fileDto);
    }

    @GetMapping("/task/{task_id}")
    public ResponseEntity<List<FileDto>> getFilesByOrderId(@PathVariable Long order_id) {
        return ResponseEntity.ok().body(fileService.getFilesByOrderId(order_id));
    }

    @PostMapping
    public ResponseEntity<Object> addFile(
            @RequestParam() MultipartFile file,
            @Valid @RequestParam() String description,
            @RequestParam(required = false) Long order_id) throws IOException, HttpClientErrorException.BadRequest {
            if (file.isEmpty()) {
            throw new FileNotFoundException("Add file to upload!");
        }
        if (order_id == null) {
            throw new BadRequestException("Add order_id");
        }

        FileDto addedFile = fileService.addFile(file, description, order_id);
        URI uri = URI.create(String.valueOf(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + addedFile.id)));
        return ResponseEntity.created(uri).body(addedFile);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateFile(
            @PathVariable Long id,
            @RequestParam() MultipartFile file,
            @RequestParam() String description,
            @RequestParam() Long order_id) throws IOException {
        if (file.isEmpty()) {
            throw new FileNotFoundException("Add file to upload!");
        }

        FileDto updateFile = fileService.updateFile(id, file, description, order_id);
        return ResponseEntity.ok().body(updateFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

}