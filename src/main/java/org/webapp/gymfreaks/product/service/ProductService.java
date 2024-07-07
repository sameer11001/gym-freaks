package org.webapp.gymfreaks.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.error.NullItemException;
import org.webapp.gymfreaks.product.error.ProductAlreadyExist;
import org.webapp.gymfreaks.product.error.ProductNotFoundException;
import org.webapp.gymfreaks.product.mapper.ProductMapper;
import org.webapp.gymfreaks.product.model.Product;
import org.webapp.gymfreaks.product.model.dto.ProductViewDto;
import org.webapp.gymfreaks.product.repository.CategoryRepository;
import org.webapp.gymfreaks.product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;

    public List<ProductViewDto> getAllProducts() {
        try {
            List<ProductViewDto> productViewDto = new ArrayList<>();
            // Optional<List<ProductViewDto>> productViewDtoOptional =
            // if (productViewDtoOptional.isPresent()) {
            // for (ProductViewDto product : productViewDtoOptional.get()) {
            // productViewDto.add(product);
            // }
            // CustomLogger.info("Get products successfully", productViewDto.toString());
            // return productViewDto;
            // }
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                productViewDto.add(productMapper.toProductDto(product));
            }
            return productViewDto;
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RuntimeException();
        }
    }

    public ProductViewDto getProductByProductName(String productName) {
        try {
            Optional<Product> product = productRepository.findByProductName(productName);
            if (!product.isPresent()) {
                CustomLogger.error("Product Not Found");
                throw new ProductNotFoundException();
            }
            return productMapper.toProductDto(product.get());
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RuntimeException();
        }
    }

    public ProductViewDto saveProduct(ProductViewDto productDto) {
        try {
            if (Boolean.TRUE.equals(productRepository.existsByProductName(productDto.getProductName()))) {
                CustomLogger.error("Product already exist.");
                throw new ProductAlreadyExist();
            }
            Product product = productRepository.save(productMapper.toEntity(productDto));
            return productMapper.toProductDto(product);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RuntimeException();
        }
    }

    public List<Product> insertAll(List<Product> entities) {
        if (entities == null) {
            CustomLogger.error(new NullItemException(), "Account list is null.");
            throw new NullItemException();
        }

        return productRepository.saveAll(entities);
    }
}
