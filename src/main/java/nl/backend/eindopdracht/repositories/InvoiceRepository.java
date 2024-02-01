package nl.backend.eindopdracht.repositories;

import nl.backend.eindopdracht.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findByCustomerAccountId(Long customerAccountId);
}
