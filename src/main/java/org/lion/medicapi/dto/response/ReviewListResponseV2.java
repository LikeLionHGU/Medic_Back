package org.lion.medicapi.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class ReviewListResponseV2 {
    private int reviewCount;
    private double averageRating;
    private List<ReviewResponseV2> reviews;

    public ReviewListResponseV2(int reviewCount, double averageRating, List<ReviewResponseV2> reviews) {
        this.reviewCount = reviewCount;
        this.averageRating = averageRating;
        this.reviews = reviews;
    }
}