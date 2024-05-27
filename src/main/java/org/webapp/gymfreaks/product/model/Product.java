package org.webapp.gymfreaks.product.model;

import org.webapp.gymfreaks.core.model.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @NotBlank
    @Column(name = "product_name", unique = true, nullable = false)
    private String productName;

    @NotBlank
    @Column(name = "product_price", nullable = false)
    private Float productPrice;

    @NotBlank
    @Column(name = "product_description", nullable = true)
    private String productDescription;

    @NotBlank
    @Column(name = "product_image", nullable = true)
    private String productImage;

    @NotBlank
    @Column(name = "product_thumbnail", nullable = true)
    private String productThumbnail;

    @NotBlank
    @Column(name = "product_stock", nullable = false)
    private Integer productStock;

    @NotBlank
    @Column(name = "product_live", nullable = false)
    private Boolean productLive;

    @NotBlank
    @Column(name = "product_category", nullable = false)
    private String productCategory; // TODO: this is foreign key to product category many products to one category

}
