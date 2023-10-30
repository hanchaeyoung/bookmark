package spring.bookmark.dto;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import spring.bookmark.entity.BookEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@MappedSuperclass
public class BookDto {

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

//    private String photo;

    @Column(columnDefinition = "TEXT")
    private String imgName; // 이미지 파일명

    @Column(columnDefinition = "TEXT")
    private String imgPath; // 이미지 조회 경로

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public BookEntity toEntity(){
        BookEntity bookEntity = BookEntity.builder()
                .book_id(book_id)
                .book_name(book_name)
                .book_intro(book_intro)
                .stock(stock)
                .price(price)
                .id(id)
//                .photo(photo)
                .imgName(imgName)
                .imgPath(imgPath)
                .build();
        return bookEntity;
    }

    @Builder
    public BookDto(Long book_id, String book_name, String book_intro, int stock, int price, String id, String imgName, String imgPath, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_intro = book_intro;
        this.stock = stock;
        this.price = price;
        this.id = id;
//        this.photo = photo;
        this.imgName = imgName;
        this.imgPath = imgPath;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    public BookDto(String book_name, String book_intro, int stock, int price, String id, String imgName, String imgPath) {
        this.book_name = book_name;
        this.id = id;
        this.stock = stock;
        this.price = price;
        this.book_intro = book_intro;
//        this.photo = photo;
        this.imgName = imgName;
        this.imgPath = imgPath;
    }


    public static BookDto build(BookEntity entity) {
        return BookDto.builder()
                .book_id(entity.getBook_id())
                .book_name(entity.getBook_name())
                .book_intro(entity.getBook_intro())
                .stock(entity.getStock())
                .price(entity.getPrice())
                .id(entity.getId())
//                .photo(entity.getPhoto())
                .imgName(entity.getImgName())
                .imgPath(entity.getImgPath())
                .createdDate(entity.getCreatedDate())
                .modifiedDate(entity.getModifiedDate())
                .build();
    }

    public static List<BookDto> buildList(List<BookEntity> boardEntities) {
        List<BookDto> userList = new ArrayList<>();
        for (BookEntity bookEntity : boardEntities) {
            userList.add(build(bookEntity));
        }
        return userList;
    }
}
