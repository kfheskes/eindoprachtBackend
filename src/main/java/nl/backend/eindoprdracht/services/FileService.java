package nl.backend.eindoprdracht.services;

import jakarta.transaction.Transactional;
import nl.backend.eindoprdracht.dtos.file.FileDto;
import nl.backend.eindoprdracht.exceptions.ContentNotFoundException;
import nl.backend.eindoprdracht.exceptions.RecordNotFoundException;
import nl.backend.eindoprdracht.models.File;
import nl.backend.eindoprdracht.models.Order;
import nl.backend.eindoprdracht.repositories.FileRepository;
import nl.backend.eindoprdracht.repositories.OrderRepository;
import nl.backend.eindoprdracht.utils.ConvertPdf;
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

    public FileDto addFile(MultipartFile fileUpload, String description, Long order_id) throws IOException {
        if (fileUpload.isEmpty()) {
            throw new IOException("Het geüploade bestand is leeg");
        } if (description.isEmpty() || description.isBlank()) {
            throw new ContentNotFoundException("Add description");
        }

        File newFile = new File();
        newFile.setContentType(fileUpload.getContentType());
        newFile.setData(ConvertPdf.compressPdf(fileUpload.getBytes()));
        newFile.setFilename(fileUpload.getOriginalFilename());
        newFile.setDescription(description);

        Order order = orderRepository.findById(order_id).orElseThrow(() -> new RecordNotFoundException("No order with id " + order_id));
        newFile.setOrder(order);

        fileRepository.save(newFile);
        return transferFileToDto(newFile);
    }



    private File createNewFile(MultipartFile fileUpload, String description, Long order_id) throws IOException {

        if (fileUpload.isEmpty()) {
            throw new ContentNotFoundException("Upload file please");
        }
        if (description.isEmpty() || description.isBlank()) {
            throw new ContentNotFoundException("Add description");
        }

        File newFile = new File();
        newFile.setData(fileUpload.getBytes());
        newFile.setFilename(fileUpload.getOriginalFilename());
        newFile.setDescription(description);

        Order order = orderRepository.findById(order_id).orElseThrow(() -> new RecordNotFoundException("No order with id " + order_id));
        newFile.setOrder(order);

        return newFile;
    }

    @Transactional
    public List<FileDto> getFilesByOrderId(Long orderId) {
        Iterable<File> files = fileRepository.findByOrder_Id(orderId);
        List<FileDto> fileDtos = new ArrayList<>();

        for (File f : files) {
            fileDtos.add(transferFileToDto(f));
        }
        return fileDtos;
    }

    public List<FileDto> getAllFiles() {
        List<File> files = fileRepository.findAll();
        List<FileDto> fileDtos = new ArrayList<>();

        for (File f : files) {
            fileDtos.add(transferFileToDto(f));
        }
        return fileDtos;
    }

    public byte[] getFileById(Long id) {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            throw new RecordNotFoundException("No file found with id " + id);
        }
        File file = fileOptional.get();
        return ConvertPdf.decompressPdf(file.getData());
    }

    public FileDto updateFile(Long id, MultipartFile fileUpload, String description, Long order_id) throws IOException {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            throw new RecordNotFoundException("No file found with id: " + id);
        }
        File newFile = createNewFile(fileUpload, description, order_id);
        newFile.setId(id);
        fileRepository.save(newFile);

        return transferFileToDto(newFile);
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
