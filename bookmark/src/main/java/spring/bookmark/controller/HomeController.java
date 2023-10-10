package spring.bookmark.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import spring.bookmark.dto.BookDto;
import spring.bookmark.service.BookService;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String bookList(Model model) {
        List<BookDto> bookList = bookService.getAllUsers();

        model.addAttribute("bookList", bookList);

        return "board/bookList";
    }

}
