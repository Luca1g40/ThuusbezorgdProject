package KitchenSystem.MicroService.application.port;

import KitchenSystem.MicroService.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String name);

}
