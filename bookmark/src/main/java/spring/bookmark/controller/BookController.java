package spring.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring.bookmark.dto.BookDto;
import spring.bookmark.service.BookService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

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

    @PostMapping("/post")
    public String write(BookDto bookDto, Authentication authentication) {
        String id = authentication.getName();
        bookDto.setId(id);
        bookService.savePost(bookDto);
        return "redirect:/";
    }

//    @PostMapping("/post")
//    public String write(
//            @RequestParam("book_name") String book_name,
//            @RequestParam("id") String id,
//            @RequestParam("stock") int stock,
//            @RequestParam("price") int price,
//            @RequestParam("book_intro") String book_intro,
//            @RequestParam("photo") MultipartFile photo,
//            Authentication authentication
//    ) {
//        // 파일 업로드 로직 추가
//        String fileName = StringUtils.cleanPath(photo.getOriginalFilename());
//        // 파일 저장 로직 추가 (파일 경로를 어딘가에 저장)
//
//        // 나머지 게시글 저장 로직 추가
//        BookDto bookDto = new BookDto(book_name, id, stock, price, book_intro, fileName);
//        bookService.savePost(bookDto);
//        return "redirect:/";
//    }

    public void saveFile(MultipartFile file, String uploadDir) {
        try {
            // 업로드 디렉토리 생성 (만약 존재하지 않을 경우)
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // 파일 저장 경로 설정
            Path copyLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));

            // 파일 복사
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리를 추가하여 파일 저장 실패 시 적절한 조치를 취할 수 있습니다.
        }
    }


    /* 게시글 수정 */
    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        BookDto bookDTO = bookService.getPost(no);

        model.addAttribute("bookDto", bookDTO);
        return "board/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(BookDto boardDTO) {
        bookService.savePost(boardDTO);

        return "redirect:/post/{no}";
    }

    /* 게시글 삭제 */
    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        bookService.deletePost(no);

        return "redirect:/";
    }

}
