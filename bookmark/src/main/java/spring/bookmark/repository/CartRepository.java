package spring.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import spring.bookmark.entity.BookEntity;
import spring.bookmark.entity.CartEntity;
import spring.bookmark.entity.UserEntity;

import java.util.List;

public interface CartRepository extends JpaRepository<CartEntity, Long> {
    // 사용자와 책을 기반으로 장바구니 항목을 찾는 메서드
    CartEntity findByUserAndBook(UserEntity user, BookEntity book);

    // 사용자 별로 장바구니 정보를 가져오는 메서드
    List<CartEntity> findByUser(UserEntity user);
}
