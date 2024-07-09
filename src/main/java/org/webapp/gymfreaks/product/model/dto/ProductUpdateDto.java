package org.webapp.gymfreaks.product.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDto {

    private String productName;

    private Float productPrice;

    private String productDescription;

    private String productImage;

    private String productThumbnail;

    private Integer productStock;

    private Boolean productLive;

    private String productCategory;
}
