package spring.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.bookmark.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
     public Optional<UserEntity> findById(String id);

    // Optional<UserEntity> findByName(String name);
}

