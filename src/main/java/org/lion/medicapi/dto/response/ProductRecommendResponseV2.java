package org.lion.medicapi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ProductRecommendResponseV2 {
    private String userName;
    private List<ProductSimpleResponseV2> recommendedProducts;

    public ProductRecommendResponseV2(String userName, List<ProductSimpleResponseV2> recommendedProducts) {
        this.userName = userName;
        this.recommendedProducts = recommendedProducts;
    }
}