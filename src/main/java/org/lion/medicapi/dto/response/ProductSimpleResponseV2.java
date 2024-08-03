package org.lion.medicapi.dto.response;

import lombok.Data;
import org.lion.medicapi.type.TagType;

@Data
public class ProductSimpleResponseV2 {

    private Long id;
    private String imageUrl;
    private String name;
    private Integer normalPrice;
    private Integer salePrice;
    private Integer reviewCnt;
    private TagType tag;

    public ProductSimpleResponseV2(Long id, String imageUrl, String name, Integer normalPrice, Integer salePrice, Integer reviewCnt, TagType tag) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.reviewCnt = reviewCnt;
        this.tag = tag;
    }
}
