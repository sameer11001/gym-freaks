package org.webapp.gymfreaks.product.controller;

import org.springframework.web.bind.annotation.*;
import org.webapp.gymfreaks.core.config.CustomLogger;

import org.webapp.gymfreaks.core.error.RunTimeException;
import org.webapp.gymfreaks.core.model.dto.CustomApiResponse;
import org.webapp.gymfreaks.product.model.dto.ProductViewDto;
import org.webapp.gymfreaks.product.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get_all")
    public CustomApiResponse<List<ProductViewDto>> getAllProducts() {
        try {
            return CustomApiResponse.successOf(productService.getAllProducts(), "Get products successfully", null);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException(e.toString());
        }
    }

    @GetMapping("/get_by_name/{name}")
    public CustomApiResponse<ProductViewDto> getProductByProductName(@PathVariable String name) {
        try {
            ProductViewDto productViewDto = productService.getProductByProductName(name);
            CustomLogger.info("Get product successfully", productViewDto.toString());
            return CustomApiResponse.successOf(productViewDto,
                    "Get product successfully", null);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException(e.toString());
        }
    }

    @PostMapping("/insert_product")
    public CustomApiResponse<ProductViewDto> insertSingleProduct(@RequestBody ProductViewDto requestBody) {
        try {
            ProductViewDto productViewDto = productService.saveProduct(requestBody);
            return CustomApiResponse.successOf(productViewDto, "Insert product successfully", null);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException(e.toString());
        }
    }
    public CustomApiResponse<List<ProductViewDto>> insertListOfProducts(@RequestBody List<ProductViewDto> requestBody) {
        try {
            List<ProductViewDto> productViewDto = productService.insertAll(requestBody);
            return CustomApiResponse.successOf(productViewDto, "Insert products successfully", null);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException(e.toString());
        }
    }
    @PutMapping("/update_product/{id}")
    public CustomApiResponse<ProductViewDto> updateSingleProduct(@RequestBody ProductViewDto requestBody ,@PathVariable Long id) {
        try {
            ProductViewDto productViewDto = productService.saveProduct(requestBody);
            return CustomApiResponse.successOf(productViewDto, "Update product successfully", null);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException(e.toString());
        }
    }

    @DeleteMapping("/delete_product/{id}")
    public CustomApiResponse<ProductViewDto> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return CustomApiResponse.successOf(null, "Delete product successfully", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RunTimeException(e.toString());
        }
    }
}
