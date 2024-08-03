package org.lion.medicapi.dto.response;

import lombok.Data;
import org.lion.medicapi.type.TagType;

@Data
public class ProductDetailResponseV2 {
    private Long id;
    private String imageUrl;
    private String name;
    private String manufacturer;
    private Integer normalPrice;
    private Integer salePrice;
    private String reportNumber;
    private String registerDate;
    private String expirationDate;
    private String intakeMethod;
    private String ingestPrecaution;
    private String functionallyContents;
    private String otherMaterials;
    private TagType tag;

    public ProductDetailResponseV2(Long id, String imageUrl, String name, String manufacturer, Integer normalPrice, Integer salePrice, String reportNumber, String registerDate, String expirationDate, String intakeMethod, String ingestPrecaution, String functionallyContents, String otherMaterials, TagType tag) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.manufacturer = manufacturer;
        this.normalPrice = normalPrice;
        this.salePrice = salePrice;
        this.reportNumber = reportNumber;
        this.registerDate = registerDate;
        this.expirationDate = expirationDate;
        this.intakeMethod = intakeMethod;
        this.ingestPrecaution = ingestPrecaution;
        this.functionallyContents = functionallyContents;
        this.otherMaterials = otherMaterials;
        this.tag = tag;
    }
}
