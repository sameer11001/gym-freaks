package org.webapp.gymfreaks.product.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.webapp.gymfreaks.core.repository.BaseRepository;
import org.webapp.gymfreaks.product.model.Category;

@Repository
public interface CategoryRepository extends BaseRepository<Category, Long> {
    Optional<Category> findByCategoryName(String categoryName);
}
