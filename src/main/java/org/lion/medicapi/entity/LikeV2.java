package org.lion.medicapi.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "like_v2")
public class LikeV2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_v2_id", nullable = false)
    private UserV2 user; // 추천인 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_v2_id", nullable = false)
    private ReviewV2 review; // 리뷰 ID

    public LikeV2(UserV2 user, ReviewV2 review) {
        this.user = user;
        this.review = review;
    }
}
