package spring.bookmark.dto;

import lombok.*;
import spring.bookmark.entity.BookEntity;
import spring.bookmark.entity.UserEntity;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CartDto {
    private Long cartId;
    private BookEntity book;
    private int quantity;
    private UserEntity user;
}
