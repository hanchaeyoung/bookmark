package spring.bookmark.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring.bookmark.dto.BookDto;
import spring.bookmark.entity.BookEntity;
import spring.bookmark.repository.BookRepository;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BookService {

    private BookRepository bookRepository;

    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookDto read(Long book_id) {
        Optional<BookEntity> optional = bookRepository.findById(book_id);
        if (optional.isPresent()) {
            BookEntity entity = optional.get();
            return BookDto.build(entity);
        } else {
            throw new IllegalArgumentException();
        }
    }

    // 모든 책 목록을 가져오는 메서드
    public List<BookDto> getAllUsers() {
        List<BookEntity> boardEntities = bookRepository.findAll();
        return BookDto.buildList(boardEntities);
    }

    @Transactional
    public BookDto getPost(Long id) {
        Optional<BookEntity> boardEntityWrapper = bookRepository.findById(id);
        BookEntity bookEntity = boardEntityWrapper.get();

        return this.convertEntityToDto(bookEntity);
    }

    private BookDto convertEntityToDto(BookEntity bookEntity) {
        return BookDto.builder()
                .book_id(bookEntity.getBook_id())
                .book_name(bookEntity.getBook_name())
                .book_intro(bookEntity.getBook_intro())
                .stock(bookEntity.getStock())
                .price(bookEntity.getPrice())
                .id(bookEntity.getId())
//                .photo(bookEntity.getPhoto())
                .imgName(bookEntity.getImgName())
                .imgPath(bookEntity.getImgPath())
                .createdDate(bookEntity.getCreatedDate())
                .modifiedDate(bookEntity.getModifiedDate())
                .build();
    }

    @Transactional
    public Long savePost(BookDto bookDto) {
        return bookRepository.save(bookDto.toEntity()).getBook_id();
    }

    /* 이미지 저장 */
    public void savePhoto(BookEntity bookEntity, MultipartFile imgFile, String existingImg) throws IOException {
        // 기존 이미지를 삭제하지 않고, 새 이미지가 업로드되면 그것을 사용하도록 변경
        String imgName = existingImg;

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files/";

        if (imgFile != null && !imgFile.isEmpty()) {
            String oriImgName = imgFile.getOriginalFilename();

            // UUID를 이용하여 파일명 생성
            UUID uuid = UUID.randomUUID();
            String savedFileName = uuid + "_" + oriImgName;

            imgName = savedFileName;

            File saveFile = new File(projectPath, imgName);
            imgFile.transferTo(saveFile);
        }

        bookEntity.setImgName(imgName);
        bookEntity.setImgPath("/files/" + imgName);

        bookRepository.save(bookEntity);
    }

    @Transactional
    public void deletePost(Long id) {
        bookRepository.deleteById(id);
    }

}