package spring.bookmark.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.bookmark.dto.BookDto;
import spring.bookmark.entity.BookEntity;
import spring.bookmark.repository.BookRepository;

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
}