package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
