package org.example.health_back.repository;


import org.example.health_back.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        Optional<User> findByUserEmailAndUserPassword(String email, String password);
        boolean existsByUserEmail(String userEmail);


}