package org.lion.medicapi.repository;

import org.lion.medicapi.entity.ReviewV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepositoryV2 extends JpaRepository<ReviewV2, Long> {

    @Query("SELECT r FROM ReviewV2 r WHERE r.product.id = :productId")
    List<ReviewV2> findByProductId(@Param("productId") Long productId);

    @Query("SELECT COUNT(r) FROM ReviewV2 r WHERE r.product.id = :productId")
    Long countByProductId(@Param("productId") Long productId);
}
