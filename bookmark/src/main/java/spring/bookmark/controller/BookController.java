package spring.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.bookmark.dto.BookDto;
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

    @Value("${upload.dir}")
    private String uploadDir;

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
    public String write(BookDto bookDto, Authentication authentication,
                        @RequestParam("file") MultipartFile file) {

        String id = authentication.getName();
        bookDto.setId(id);

        // 파일을 업로드하고 저장
        if (!file.isEmpty()) {
            try {
                // 파일을 저장할 경로 설정
                 String uploadDir = "/uploads";
                Path uploadPath = Paths.get(uploadDir);

                // 파일명 생성
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);

                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                // 파일 저장
                Files.copy(file.getInputStream(), filePath);

                // 엔티티에 파일 경로 저장
                bookDto.setPhoto("uploads/" + fileName); // 파일 이름을 저장
                // bookDto.setPhoto(fileName); // 파일 이름을 저장
            } catch (IOException e) {
                e.printStackTrace();
                // 파일 업로드 실패 처리
                return "redirect:/board/write.html?error=upload_error";
            }
        }

        bookService.savePost(bookDto);
        return "redirect:/";
    }

    /* 게시글 수정 */
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BookDto bookDTO = bookService.getPost(no);

        model.addAttribute("bookDto", bookDTO);
        return "board/update.html";
    }

    @PostMapping("/post/edit/{no}")
    public String update(@PathVariable("no") Long no, BookDto bookDto) {
        bookDto.setBook_id(no);

        bookService.savePost(bookDto);

        return "redirect:/board/post/{no}";
    }

    /* 게시글 삭제 */
    @PostMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        bookService.deletePost(no);

        return "redirect:/";
    }
}
