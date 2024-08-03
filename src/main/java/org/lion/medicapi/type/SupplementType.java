package org.lion.medicapi.type;

import org.lion.medicapi.exception.ApiException;
import org.lion.medicapi.exception.ErrorType;

public enum SupplementType {
    관절솔루션_보스웰리아_7("관절솔루션 보스웰리아 7"),
    굿바디_카르니틴_1000("굿바디 카르니틴 1000"),
    세이헬스_녹차추출물("세이헬스 녹차추출물"),
    피부건강엔_N_실키어린_콜라겐("피부건강엔 N 실키어린 콜라겐"),
    바디픽_멀티비타민_포우먼("바디픽 멀티비타민 포우먼"),
    엑스트라_스트렝스_징코("엑스트라 스트렝스 징코"),
    아르기닌_파워_젤리스틱("아르기닌 파워 젤리스틱"),
    내츄럴플러스_알티지_오메가3("내츄럴플러스 알티지 오메가3");

    private final String productName;

    SupplementType(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public static SupplementType fromProductName(String productName) {
        for (SupplementType type : SupplementType.values()) {
            if (type.getProductName().equals(productName)) {
                return type;
            }
        }
        throw new ApiException(ErrorType.SUPPLEMENT_TYPE_NOT_FOUND);
    }

    public String getRecommendedProduct() {
        switch (this) {
            case 관절솔루션_보스웰리아_7:
                return "프리미엄 관절팔팔";
            case 굿바디_카르니틴_1000:
                return "베러다운핏";
            case 세이헬스_녹차추출물:
                return "뺀다요";
            case 피부건강엔_N_실키어린_콜라겐:
                return "뺀다요";
            case 바디픽_멀티비타민_포우먼:
                return "센트룸 프로바이오 면역케어";
            case 엑스트라_스트렝스_징코:
                return "헬시부트 올데이샷";
            case 아르기닌_파워_젤리스틱:
                return "헬퓨 E데이펙";
            case 내츄럴플러스_알티지_오메가3:
                return "헬퓨 E데이펙";
            default:
                throw new ApiException(ErrorType.SUPPLEMENT_TYPE_NOT_FOUND);
        }
    }
}