package spring.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import spring.bookmark.dto.BookDto;
import spring.bookmark.service.BookService;

import java.util.List;

@Controller
@RequestMapping("/board")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    public void setBoardService(BookService bookService) {
        this.bookService = bookService;
    }


    @GetMapping("/list")
    public String boardList(Model model) {
        List<BookDto> boardList = bookService.getAllUsers();

        model.addAttribute("boardList", boardList);

        return "board/boardList";
    }

}
