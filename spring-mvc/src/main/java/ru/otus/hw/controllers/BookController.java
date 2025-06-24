package ru.otus.hw.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.hw.dto.BookDto;
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

    @PostMapping("/book")
    public String createBook(BookDto bookDto) {
        bookService.insert(bookDto.getTitle(), bookDto.getAuthorId(), bookDto.getGenreId());
        return "redirect:/";
    }

    @GetMapping("/book/{id}")
    public String showBook(@PathVariable Long id, Model model) {
        var optionalBook = bookService.findById(id);
        optionalBook.ifPresent(book -> model.addAttribute("book", book));
        return "edit";
    }

    @PostMapping("/book/{id}")
    public String updateBook(@PathVariable Long id, @RequestParam(name = "title") String title) {
        var optionalBook = bookService.findById(id);
        optionalBook.ifPresent(bookCurrent -> bookService.update(id, title, bookCurrent.getAuthor().getId(),
                bookCurrent.getGenre().getId()));
        return "redirect:/";
    }

    @PostMapping("/book/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

}
