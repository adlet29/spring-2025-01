package ru.otus.hw.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.controllers.BookController;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Тестирование контроллер для CRUD операций над книгами")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private Book book;
    private Author author;
    private Genre genre;

    @BeforeEach
    void setup() {
        author = new Author(1L, "Author_1");
        genre = new Genre(1L, "Genre_1");
        book = new Book(1L, "BookTitle_10500", author, genre);
    }


    @Test
    @DisplayName("отображение главной страницы со списком книг, авторов и жанров")
    void testListPage() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attributeExists("authors"))
                .andExpect(model().attributeExists("genres"));
        verify(bookService).findAll();
        verify(authorService).findAll();
        verify(genreService).findAll();
    }

    @Test
    @DisplayName("добавление новой книги и редирект на главную страницу")
    void testAddBook() throws Exception {
        given(bookService.insert("New Book", 1L, 1L)).willReturn(book);

        mockMvc.perform(post("/add")
                        .param("title", "New Book")
                        .param("authorId", "1")
                        .param("genreId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService).insert("New Book", 1L, 1L);
    }

    @Test
    @DisplayName("обновление книги и отображение страницы редактирования")
    void testShowBookForEdit() throws Exception {
        given(bookService.findById(1L)).willReturn(Optional.of(book));

        mockMvc.perform(get("/edit").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attributeExists("book"));

        verify(bookService).findById(1L);
    }

    @Test
    @DisplayName("удаление книги и редирект на главную страницу")
    void testDeleteBook() throws Exception {
        mockMvc.perform(get("/delete").param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(bookService).deleteById(1L);
    }


}
