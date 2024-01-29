package nl.backend.eindoprdracht.services;

import nl.backend.eindoprdracht.repositories.InvoiceRepository;
import nl.backend.eindoprdracht.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class FileService {


private final UserRepository userRepository;
private final InvoiceRepository invoiceRepository;


    public FileService(UserRepository userRepository, InvoiceRepository invoiceRepository) {
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
    }




}
