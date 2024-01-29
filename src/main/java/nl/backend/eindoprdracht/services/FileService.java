package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.repositories.FileRepository;
import nl.backend.eindoprdracht.repositories.InvoiceRepository;
import nl.backend.eindoprdracht.repositories.OrderRepository;
import nl.backend.eindoprdracht.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
            throw new RecordNotFoundException("Geen bestand gevonden met id: " + id);
        }

        File file = fileOptional.get();

        return transferFileToDto(file);
    }

    @Transactional(readOnly = true)
    public List<FileDto> getFilesByTaskId(Long taskId) {
        Iterable<File> files = fileRepository.findByTask_Id(taskId);
        List<FileDto> fileDtos = new ArrayList<>();

        for (File f : files) {
            fileDtos.add(transferFileToDto(f));
        }
        return fileDtos;
    }

    public FileDto addFile(MultipartFile fileUpload, String description, Long task_id) throws IOException {
        //Foutmeldingen worden afgehandeld in createNewFile
        File newFile = createNewFile(fileUpload, description, task_id);
        fileRepository.save(newFile);
        return transferFileToDto(newFile);
    }

    public FileDto updateFile(Long id, MultipartFile fileUpload, String description, Long task_id) throws IOException {
        Optional<File> fileOptional = fileRepository.findById(id);
        if (fileOptional.isEmpty()) {
            throw new RecordNotFoundException("Geen bestand gevonden met id: " + id);
        }
        //Overige foutmeldingen worden afgehandeld in createNewFile
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
            throw new ContentNotFoundException("Je moet nog een bestand uploaden");
        }
        if (description.isEmpty() || description.isBlank()) {
            throw new ContentNotFoundException("Voeg nog een beschrijving toe voor je afbeelding");
        }

        File newFile = new File();
        newFile.setData(fileUpload.getBytes());
        newFile.setMimeType(fileUpload.getContentType());
        newFile.setFilename(fileUpload.getOriginalFilename());
        newFile.setDescription(description);

        Task task = taskRepository.findById(task_id).orElseThrow(() -> new RecordNotFoundException("Er is geen taak met id: " + task_id));
        newFile.setTask(task);

        return newFile;
    }

    public FileDto transferFileToDto(File file) {
        FileDto fileDto = new FileDto();

        fileDto.id = file.getId();
        fileDto.filename = file.getFilename();
        fileDto.description = file.getDescription();
        fileDto.data = file.getData();
        fileDto.mimeType = file.getMimeType();
        fileDto.task = file.getTask();

        return fileDto;
    }

    public File transferDtoToFile(FileDto fileDto) {
        File file = new File();

        // Geen setId nodig, deze genereert de database of staat in de URL
        file.setFilename(fileDto.filename);
        file.setDescription(fileDto.description);
        file.setData(fileDto.data);
        file.setMimeType(fileDto.mimeType);
        file.setTask(fileDto.task);

        return file;
    }





}
