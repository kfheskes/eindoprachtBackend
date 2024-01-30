package nl.backend.eindopdracht.repositories;

import nl.backend.eindopdracht.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
