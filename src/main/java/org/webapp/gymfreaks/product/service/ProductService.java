package org.webapp.gymfreaks.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webapp.gymfreaks.product.model.Product;
import org.webapp.gymfreaks.product.repository.CategoryRepository;
import org.webapp.gymfreaks.product.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductByProductName(String productName) {
        try {
            Optional<Product> product = productRepository.findByProductName(productName);
            if (!product.isPresent()) {
                throw new RuntimeException();// TODO product not found exception

            }
            return product.get();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void saveProduct(Product product) {

        try {
            productRepository.save(product);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
