package spring.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.bookmark.dto.BookDto;
import spring.bookmark.entity.BookEntity;
import spring.bookmark.service.BookService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/board")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookDto bookDto;

    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @Autowired
    public void setBookDto(BookDto bookDto) {
        this.bookDto = bookDto;
    }

    /* 게시글 상세 */
    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        BookDto bookDto = bookService.getPost(no);

        model.addAttribute("bookDto", bookDto);
        return "board/detail.html";
    }

    /* 게시글 쓰기 */
    @GetMapping("/post")
    public String write() {
        return "board/write.html";
    }

//    @PostMapping("/post")
//    public String write(BookDto bookDto, Authentication authentication) {
//        String id = authentication.getName();
//        bookDto.setId(id);
//        bookService.savePost(bookDto);
//        return "redirect:/";
//    }

    @PostMapping("/post")
//    public String write(BookEntity bookEntity, MultipartFile imgFile) throws Exception {
//        bookService.savePhoto(bookEntity, imgFile);
//
//        return "redirect:/";
//    }
    public String write(BookEntity bookEntity, Authentication authentication, MultipartFile imgFile) {
        try {
            String id = authentication.getName();
            bookEntity.setId(id);

            // 기존 이미지 파일명을 빈 문자열로 초기화
            String existingImg = "";

            // 이미지를 업로드한 경우, 기존 이미지 파일명을 가져옴
//            if (bookEntity.getId() != null) {
//                BookDto existingBoardDto = bookService.getPost(bookEntity.getBook_id());
//                existingImg = existingBoardDto.getImgName();
//            }

            bookService.savePhoto(bookEntity, imgFile, existingImg);
            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();
            return "error_page";
        }
    }

    /* 게시글 수정 */
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BookDto bookDTO = bookService.getPost(no);

        model.addAttribute("bookDto", bookDTO);
        return "board/update.html";
    }

    @PostMapping("/post/edit/{no}")
//    public String update(@PathVariable("no") Long no, BookDto bookDto) {
//        bookDto.setBook_id(no);
//
//        bookService.savePost(bookDto);
//
//        return "redirect:/board/post/{no}";
//    }
    public String update(@PathVariable("no") Long no, BookDto bookDto, @RequestParam("imgFile") MultipartFile imgFile) {
        bookDto.setBook_id(no);

        // 기존 이미지 파일명을 가져옴
        BookDto existingBookDto = bookService.getPost(no);
        String existingImg = existingBookDto.getImgName();

        // 이미지 수정 메소드 호출
        try {
            bookService.savePhoto(bookDto.toEntity(), imgFile, existingImg);
            return "redirect:/board/post/" + no;
        } catch (IOException e) {
            e.printStackTrace();
            return "error_page";
        }
    }

    /* 게시글 삭제 */
    @PostMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        bookService.deletePost(no);

        return "redirect:/";
    }
}
