package org.webapp.gymfreaks.core.config;

import java.util.ArrayList;

import java.util.List;

import java.io.File;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Component;
import org.webapp.gymfreaks.auth.model.UserEntity;
import org.webapp.gymfreaks.auth.model.Role;
import org.webapp.gymfreaks.auth.service.AuthService;
import org.webapp.gymfreaks.auth.service.RoleService;
import org.webapp.gymfreaks.auth.utils.constants.Authorities;
import org.webapp.gymfreaks.auth.utils.constants.Gender;
import org.webapp.gymfreaks.product.mapper.ProductMapper;
import org.webapp.gymfreaks.product.model.Category;
import org.webapp.gymfreaks.product.model.Product;
import org.webapp.gymfreaks.product.model.dto.ProductViewDto;
import org.webapp.gymfreaks.product.service.CategoryService;
import org.webapp.gymfreaks.product.service.ProductService;

import java.nio.file.Path;
import java.nio.file.Paths;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StartUpDataApp implements CommandLineRunner {

    @Autowired
    AuthService accountService;

    @Autowired
    RoleService roleService;

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ProductMapper productMapper;

    private static final String LOCATION = "src\\main\\resources\\static\\user_photo\\";
    private static final String FILENAME = "user_photo_default.jpg";


    @Override
    public void run(String ... args) throws RuntimeException {
        log.debug("StartUpDataApp started");

        try {


            File file = new File(LOCATION + FILENAME);
            if (!file.exists()) {
                log.debug("File not found");
            }
            Path path = Paths.get(file.toURI());

            UrlResource resource = new UrlResource(path.toUri());
            List<UserEntity> testAccount = new ArrayList<>();

            UserEntity accountAdmin = new UserEntity();
            if (roleService.findAllRoles().isEmpty()) {
                roleService.saveRole(new Role(null, Authorities.ADMIN.toString()));
                roleService.saveRole(new Role(null, Authorities.USER.toString()));
                roleService.saveRole(new Role(null, Authorities.EDITOR.toString()));

            }

            if (categoryService.getAllCategories().isEmpty()) {
                categoryService.saveCategory(new Category(null, "test_category"));
            }

            if (accountService.findAll().isEmpty()) {

                for (int i = 0; i < 10; i++) {
                    UserEntity account = new UserEntity();
                    account.setUserEmail("test" + i + "@freaks.com");
                    account.setUserPassword(passwordEncoder.encode("test" + i));
                    account.setUserFirstName("test" + i);
                    account.setUserLastName("_user");
                    account.setUserPhoneNumber("067556234" + i);
                    account.setUserDescription("im a test user_" + i);
                    account.setUserGender(Gender.MALE.toString());
                    account.setUserImage(resource.getFilename());
                    account.setUserRoles(roleService.findRoleByRoleName(Authorities.USER.toString()));
                    log.debug(account.toString());
                    testAccount.add(account);

                }
                log.debug(testAccount.toString());

                accountService.insertAll(testAccount);
                log.debug("inserted 10 users");
                accountAdmin.setUserEmail("x@freaks.com");
                accountAdmin.setUserPassword(passwordEncoder.encode("admin"));
                accountAdmin.setUserFirstName("admin_x");
                accountAdmin.setUserPhoneNumber("0000000000");
                accountAdmin.setUserRoles(roleService.findRoleByRoleName(Authorities.ADMIN.toString()));
                accountService.insert(accountAdmin);

                log.debug(accountAdmin.toString());


            }

            if (productService.getAllProducts().isEmpty()) {
                List<Product> testProducts = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    Product product = Product.builder()
                            .productName("TestProduct" + i)
                            .productPrice(10.0f + i)
                            .productDescription("Description for TestProduct" + i)
                            .productImage("image" + i + ".jpg")
                            .productThumbnail("thumbnail" + i + ".jpg")
                            .productStock(i)
                            .productLive(true)
                            .productCategory(categoryService.getCategoryByCategoryName("test_category").getCategoryName())
                            .build();

                    testProducts.add(product);
                }
                List<ProductViewDto> productViewDto = new ArrayList<>();
                for (Product product : testProducts) {
                    productViewDto.add(productMapper.toProductDto(product));
                }
                productService.insertAll(productViewDto);
            }
        }catch (Exception e) {
            log.debug(e.getMessage());
        }
    }

}
