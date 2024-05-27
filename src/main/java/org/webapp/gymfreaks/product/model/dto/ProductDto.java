package org.webapp.gymfreaks.product.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ProductDto {

    @NotBlank
    private String productName;

    @NotBlank
    private Float productPrice;

    private String productDescription;

    private String productImage;

    private String productThumbnail;

    private Integer productStock;

    private Boolean productLive;

}
