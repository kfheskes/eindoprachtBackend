package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.dtos.file.FileDto;
import nl.backend.eindoprdracht.exceptions.ContentNotFoundException;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.File;
import nl.backend.eindoprdracht.models.Order;
import nl.backend.eindoprdracht.repositories.FileRepository;
import nl.backend.eindoprdracht.repositories.OrderRepository;
import nl.backend.eindoprdracht.utils.ConvertPdf;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.util.Optional;

@Service
public class FileService {

    private final FileRepository fileRepository;
    private final OrderRepository orderRepository;

    public FileService(FileRepository fileRepository, OrderRepository orderRepository) {
        this.fileRepository = fileRepository;
        this.orderRepository = orderRepository;
    }

    public FileDto addFile(MultipartFile fileUpload, String description, Long order_id) throws IOException {
        if (fileUpload.isEmpty()) {
            throw new IOException("Het geüploade bestand is leeg");
        }
        if (description.isEmpty() || description.isBlank()) {
            throw new ContentNotFoundException("Add description");
        }

        File newFile = new File();
        newFile.setContentType(fileUpload.getContentType());
        newFile.setData(ConvertPdf.compressBytes(fileUpload.getBytes()));
        newFile.setFilename(fileUpload.getOriginalFilename());
        newFile.setDescription(description);

        Order order = orderRepository.findById(order_id).orElseThrow(() -> new RecordNotFoundException("No order with id " + order_id));
        newFile.setOrder(order);

        fileRepository.save(newFile);
        return transferFileToDto(newFile);
    }

    public byte[] getFileById(Long id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            throw new RecordNotFoundException("No file found with id " + id);
        }
        File file = fileOptional.get();
        return ConvertPdf.decompressBytes(file.getData());
    }


    public void deleteFile(Long id) {
        Optional<File> optionalFile = fileRepository.findById(id);
        if (optionalFile.isEmpty()) {
            throw new RecordNotFoundException("No file found with id:" + id);
        }
        fileRepository.deleteById(id);
    }


    public FileDto transferFileToDto(File file) {
        FileDto fileDto = new FileDto();

        fileDto.id = file.getId();
        fileDto.filename = file.getFilename();
        fileDto.description = file.getDescription();
        fileDto.data = file.getData();
        fileDto.contentType = file.getContentType();
        fileDto.order = file.getOrder();

        return fileDto;
    }

    public File transferDtoToFile(FileDto fileDto) {
        File file = new File();


        file.setFilename(fileDto.filename);
        file.setDescription(fileDto.description);
        file.setData(fileDto.data);
        file.setOrder(fileDto.order);

        return file;
    }


}
