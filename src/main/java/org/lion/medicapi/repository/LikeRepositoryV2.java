package org.lion.medicapi.repository;

import org.lion.medicapi.entity.LikeV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepositoryV2 extends JpaRepository<LikeV2, Long> {

    @Query("SELECT COUNT(l) FROM LikeV2 l WHERE l.review.id = :reviewId")
    Long countByReviewId(@Param("reviewId") Long reviewId);
}
