package org.lion.medicapi.dto.response;

import lombok.Data;
import org.lion.medicapi.type.GenderType;

@Data
public class ReviewResponseV2 {

    private Long id;
    private String userName;
    private String productName;
    private GenderType gender;
    private String ageRange;
    private Integer star;
    private String contents;
    private String purchaseDate;
    private String reviewDate;
    private Integer likeCnt;

    public ReviewResponseV2(Long id, String userName, String productName, GenderType gender, String ageRange, Integer star, String contents, String purchaseDate, String reviewDate, Integer likeCnt) {
        this.id = id;
        this.userName = userName;
        this.productName = productName;
        this.gender = gender;
        this.ageRange = ageRange;
        this.star = star;
        this.contents = contents;
        this.purchaseDate = purchaseDate;
        this.reviewDate = reviewDate;
        this.likeCnt = likeCnt;
    }
}