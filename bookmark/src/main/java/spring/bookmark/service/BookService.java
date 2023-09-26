package spring.bookmark.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.bookmark.dto.BookDto;
import spring.bookmark.entity.BookEntity;
import spring.bookmark.repository.BookRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
                .photo(bookEntity.getPhoto())
                .createdDate(bookEntity.getCreatedDate())
                .modifiedDate(bookEntity.getModifiedDate())
                .build();
    }

    @Transactional
    public Long savePost(BookDto bookDto) {
        return bookRepository.save(bookDto.toEntity()).getBook_id();
    }

    @Transactional
    public void deletePost(Long id) {
        bookRepository.deleteById(id);
    }

}