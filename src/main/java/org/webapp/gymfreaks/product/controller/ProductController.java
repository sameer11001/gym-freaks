package org.webapp.gymfreaks.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.model.dto.CustomApiResponse;
import org.webapp.gymfreaks.product.model.dto.ProductViewDto;
import org.webapp.gymfreaks.product.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
            throw new RuntimeException();
        }
    }

    @GetMapping("/get_by_name")
    public CustomApiResponse<ProductViewDto> getProductByProductName(@RequestParam String productName) {
        try {
            ProductViewDto productViewDto = productService.getProductByProductName(productName);
            CustomLogger.info("Get product successfully", productViewDto.toString());
            return CustomApiResponse.successOf(productViewDto,
                    "Get product successfully", null);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RuntimeException();
        }
    }

    @PostMapping("/insert_product")
    public CustomApiResponse<ProductViewDto> postMethodName(@RequestBody ProductViewDto requestBody) {
        try {
            ProductViewDto productViewDto = productService.saveProduct(requestBody);
            return CustomApiResponse.successOf(productViewDto, "Insert product successfully", null);
        } catch (Exception e) {
            CustomLogger.error(new RuntimeException(), "Error " + e.toString());
            throw new RuntimeException();
        }
    }

}
