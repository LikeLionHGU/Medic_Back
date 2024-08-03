package org.lion.medicapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.lion.medicapi.type.TagType;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "product_v2")
public class ProductV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID

    @Column(nullable = false)
    private String name; // 제품 명

    private String imageUrl; // 이미지 경로

    private String manufacturer; // 제조사 명

    private Integer normalPrice; // 정상 가격

    private Integer salePrice; // 판매 가격

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TagType tagType; // 태그

    private String reportNumber; // 신고 번호

    private String registerDate; // 등록일 (yyyyMMdd)

    private String expirationDate; // 유통기한 (ex. 제조일로부터 24개월)

    private String intakeMethod; // 섭취량 and 섭취 방법

    private String ingestPrecaution; // 섭취 시 주의사항

    private String functionallyContents; // 기능성 내용

    private String otherMaterials; // 기타 원료
}
