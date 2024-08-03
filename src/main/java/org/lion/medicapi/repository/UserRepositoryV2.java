package org.lion.medicapi.repository;

import org.lion.medicapi.entity.UserV2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositoryV2 extends JpaRepository<UserV2, Long> {

    boolean existsByEmail(String email);
    boolean existsByName(String name);
    Optional<UserV2> findByEmail(String email);
}
