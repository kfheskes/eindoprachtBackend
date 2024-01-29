package nl.backend.eindoprdracht.services;

import jakarta.transaction.Transactional;
import nl.backend.eindoprdracht.dtos.file.FileDto;
import nl.backend.eindoprdracht.exceptions.ContentNotFoundException;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.File;
import nl.backend.eindoprdracht.models.Order;
import nl.backend.eindoprdracht.repositories.FileRepository;
import nl.backend.eindoprdracht.repositories.OrderRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final OrderRepository orderRepository;


    public FileService(FileRepository fileRepository, OrderRepository orderRepository) {
        this.fileRepository = fileRepository;
        this.orderRepository = orderRepository;
    }

    public List<FileDto> getAllFiles() {
        Iterable<File> files = fileRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        List<FileDto> fileDtos = new ArrayList<>();

        for (File f : files) {
            fileDtos.add(transferFileToDto(f));
        }
        return fileDtos;
    }

    public FileDto getFile(Long id) {
        Optional<File> fileOptional = fileRepository.findById(id);

        if (fileOptional.isEmpty()) {
            throw new RecordNotFoundException("No file found with id " + id);
        }

        File file = fileOptional.get();

        return transferFileToDto(file);
    }

    @Transactional
    public List<FileDto> getFilesByTaskId(Long taskId) {
        List<File> files = fileRepository.findByTask_Id(taskId);
        List<FileDto> fileDtos = new ArrayList<>();

        for (File f : files) {
            fileDtos.add(transferFileToDto(f));
        }
        return fileDtos;
    }

    public FileDto addFile(MultipartFile fileUpload, String description, Long task_id) throws IOException {

        File newFile = createNewFile(fileUpload, description, task_id);
        fileRepository.save(newFile);
        return transferFileToDto(newFile);
    }

    public FileDto updateFile(Long id, MultipartFile fileUpload, String description, Long task_id) throws IOException {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            throw new RecordNotFoundException("Geen bestand gevonden met id: " + id);
        }
        File newFile = createNewFile(fileUpload, description, task_id);
        newFile.setId(id);
        fileRepository.save(newFile);

        return transferFileToDto(newFile);
    }

    public void deleteFile(Long id) {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (optionalFile.isEmpty()) {
            throw new RecordNotFoundException("Geen bestand gevonden met id: " + id);
        }
        fileRepository.deleteById(id);
    }

    private File createNewFile(MultipartFile fileUpload, String description, Long task_id) throws IOException {

        if (fileUpload.isEmpty()) {
            throw new ContentNotFoundException("Upload file please");
        }
        if (description.isEmpty() || description.isBlank()) {
            throw new ContentNotFoundException("Add description for your image");
        }

        File newFile = new File();
        newFile.setData(fileUpload.getBytes());
        newFile.setMimeType(fileUpload.getContentType());
        newFile.setFilename(fileUpload.getOriginalFilename());
        newFile.setDescription(description);

        Order order = orderRepository.findById(task_id).orElseThrow(() -> new RecordNotFoundException("No order with id " + task_id));
        newFile.setOrder(order);

        return newFile;
    }

    public FileDto transferFileToDto(File file) {
        FileDto fileDto = new FileDto();

        fileDto.id = file.getId();
        fileDto.filename = file.getFilename();
        fileDto.description = file.getDescription();
        fileDto.data = file.getData();
        fileDto.mimeType = file.getMimeType();
        fileDto.order = file.getOrder();

        return fileDto;
    }

    public File transferDtoToFile(FileDto fileDto) {
        File file = new File();


        file.setFilename(fileDto.filename);
        file.setDescription(fileDto.description);
        file.setData(fileDto.data);
        file.setMimeType(fileDto.mimeType);
        file.setOrder(fileDto.order);

        return file;
    }





}
