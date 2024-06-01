package org.webapp.gymfreaks.product.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.webapp.gymfreaks.product.model.Product;
import org.webapp.gymfreaks.product.model.dto.ProductViewDto;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productId", ignore = true)
    Product toEntity(ProductViewDto productDto);

    ProductViewDto toProductDto(Product product);

}
