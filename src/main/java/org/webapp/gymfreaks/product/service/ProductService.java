package org.webapp.gymfreaks.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.error.EmptyItemsException;
import org.webapp.gymfreaks.core.error.RunTimeException;
import org.webapp.gymfreaks.product.error.ProductAlreadyExist;
import org.webapp.gymfreaks.product.error.ProductNotFoundException;
import org.webapp.gymfreaks.product.mapper.ProductMapper;
import org.webapp.gymfreaks.product.model.Product;
import org.webapp.gymfreaks.product.model.dto.ProductViewDto;
import org.webapp.gymfreaks.product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    /**
     * @return List<ProductViewDto> returns all products
     */
    public List<ProductViewDto> getAllProducts() {
        try {
            List<ProductViewDto> productViewDto = new ArrayList<>();
            List<Product> products = productRepository.findAll();
            for (Product product : products) {
                productViewDto.add(productMapper.toProductDto(product));
            }
            return productViewDto;
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException();
        }
    }

    /**
     * @param productName name of the product
     * @return ProductViewDto
     */
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
            throw new RunTimeException();
        }
    }

    /**
     * @param productDto productDto to be inserted
     * @return ProductViewDto
     */
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
            throw new RunTimeException();
        }
    }

    /**
     * @param productDto List of products to be inserted
     * return List<ProductViewDto>
     */
    public List<ProductViewDto> insertAll(List<ProductViewDto> productDto) {
        try {
            if (productDto == null) {
                CustomLogger.error(new EmptyItemsException(), "Account list is null.");
                throw new EmptyItemsException();
            }
            List<Product> products = new ArrayList<>();
            for (ProductViewDto productViewDto : productDto) {
                products.add(productMapper.toEntity(productViewDto));
            }
            productRepository.saveAll(products);
            return productDto;

        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException();
        }
    }

    /**
     * @param  productDto productDto to be updated
     * @param  id id of the product
     * @return ProductViewDto
     */
    public ProductViewDto updateProduct(ProductViewDto productDto ,Long id) {
        try {
            if (Boolean.FALSE.equals(productRepository.existsById(id))) {
                CustomLogger.error("Product Not Found.");
                throw new ProductNotFoundException("Product Not Found");
            }
            Product product = productRepository.save(productMapper.toEntity(productDto));
            return productMapper.toProductDto(product);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException();
        }
    }

    /**
     * @param id id of the product
     * return void
     */
    public void deleteProduct(Long id) {
        try {
            if (Boolean.FALSE.equals(productRepository.existsById(id))) {
                CustomLogger.error("Product Not Found.");
                throw new ProductNotFoundException("Product Not Found");
            }
            productRepository.deleteById(id);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException();
        }
    }
}

