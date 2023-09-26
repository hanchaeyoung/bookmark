package spring.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.bookmark.entity.BookEntity;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
//     Optional<BookEntity> findById(Long book_id);
}