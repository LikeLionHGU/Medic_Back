package org.lion.medicapi.repository;

import org.lion.medicapi.entity.ProductV2;
import org.lion.medicapi.type.TagType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepositoryV2 extends JpaRepository<ProductV2, Long> {

    @Query("SELECT p FROM ProductV2 p WHERE p.tagType IN :tags")
    List<ProductV2> findByTagTypes(@Param("tags") List<TagType> tags);

    Optional<ProductV2> findByName(String name);
}
