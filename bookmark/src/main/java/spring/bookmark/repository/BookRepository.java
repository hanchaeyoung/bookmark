package spring.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.bookmark.entity.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    // Optional<BoardEntity> findByBook_id(Long book_id);
}