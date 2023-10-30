package spring.bookmark.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "book_entity")
@Getter
@Setter
public class BookEntity extends TimeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long book_id;

    @Column(nullable = false)
    private String book_name;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String book_intro;

    @Column(nullable = false)
    private int stock;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String id;

//    @Column
//    private String photo;

    @Column(columnDefinition = "TEXT")
    private String imgName; // 이미지 파일명

    @Column(columnDefinition = "TEXT")
    private String imgPath; // 이미지 조회 경로

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    @Builder
    public BookEntity(Long book_id, String book_name, String book_intro, int stock, int price, String id, String imgName, String imgPath) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_intro = book_intro;
        this.stock = stock;
        this.price = price;
        this.id = id;
//        this.photo = photo;
        this.imgName = imgName;
        this.imgPath = imgPath;
    }
}