package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        var books = bookService.findAll();
        var authors = authorService.findAll();
        var genres = genreService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "index";
    }

    @PostMapping("/add")
    public String addBook(@RequestParam String title,
                          @RequestParam Long authorId,
                          @RequestParam Long genreId,
                          Model model) {
        var book = bookService.insert(title, authorId, genreId);
        model.addAttribute("addResult", book != null);
        return "redirect:/";
    }

    @GetMapping("/edit")
    public String showBookForEdit(@RequestParam(name = "id") Long id, Model model) {
        var optionalBook = bookService.findById(id);
        if (optionalBook.isPresent()) {
            var book = optionalBook.get();
            model.addAttribute("book", book);
        }
        return "edit";
    }

    @GetMapping("/update")
    public String updateBook(@RequestParam(name = "title") String title,
                             @RequestParam(name = "id") Long id,
                             Model model) {
        var optionalBook = bookService.findById(id);
        if (optionalBook.isPresent() && !"".equals(title)) {
            var bookCurrent = optionalBook.get();
            var book = bookService.update(id, title, bookCurrent.getAuthor().getId(), bookCurrent.getGenre().getId());
            model.addAttribute("book", book);
        }
        return "edit";
    }

    @GetMapping("/delete")
    public String deleteBook(@RequestParam(name = "id") Long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

}
