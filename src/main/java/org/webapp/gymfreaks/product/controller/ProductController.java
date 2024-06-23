package org.webapp.gymfreaks.product.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webapp.gymfreaks.core.config.CustomLogger;
import org.webapp.gymfreaks.core.config.Lettuce;
import org.webapp.gymfreaks.core.model.dto.CustomApiResponse;
import org.webapp.gymfreaks.product.model.dto.ProductViewDto;
import org.webapp.gymfreaks.product.service.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    Lettuce<ProductViewDto> lettuce;

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

    @GetMapping("/get_by_name/{productName}")
    public CustomApiResponse<ProductViewDto> getProductByProductName(@PathVariable String productName) {
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

    @GetMapping("/get_product_name")
    public String getMethodName() {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("products"))) {
            return lettuce.getString("products").get();
        }
        String name = productService.getProductByProductName("TestProduct1").getProductName();
        lettuce.saveString("products", name);
        return name;
    }

}
