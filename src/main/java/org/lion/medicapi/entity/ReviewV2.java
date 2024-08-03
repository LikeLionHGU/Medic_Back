package org.lion.medicapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_v2")
public class ReviewV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_v2_id", nullable = false)
    private UserV2 user; // 작성자 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_v2_id", nullable = false)
    private ProductV2 product; // 제품 ID

    private Integer star; // 평점

    private String contents; // 내용

    private String purchaseDate; // 제품 구매일

    private String reviewDate; // 후기 작성일

    public ReviewV2(UserV2 user, ProductV2 product, Integer star, String contents, String purchaseDate, String reviewDate) {
        this.user = user;
        this.product = product;
        this.star = star;
        this.contents = contents;
        this.purchaseDate = purchaseDate;
        this.reviewDate = reviewDate;
    }
}
