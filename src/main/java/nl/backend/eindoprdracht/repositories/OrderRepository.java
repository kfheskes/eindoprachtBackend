package nl.backend.eindoprdracht.repositories;

import nl.backend.eindoprdracht.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
