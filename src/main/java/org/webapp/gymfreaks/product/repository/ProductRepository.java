package org.webapp.gymfreaks.product.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.webapp.gymfreaks.core.repository.BaseRepository;
import org.webapp.gymfreaks.product.model.Product;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

    Boolean existsByProductName(String productName);

    Optional<Product> findByProductName(String productName);
}
