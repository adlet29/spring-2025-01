package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.services.BookService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Интеграционный тест книг ")
@SpringBootTest
@TestPropertySource(properties = "spring.shell.interactive.enabled=false")
class BookServiceimplTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    private Author testAuthor;

    private Genre testGenre;

    @BeforeEach
    void setUp() {
        testAuthor = authorRepository.findById(1).orElseThrow(() -> new RuntimeException("Author not found with id 1"));
        testGenre = genreRepository.findById(1).orElseThrow(() -> new RuntimeException("Genre not found with id 1"));
    }


    @DisplayName("Проверить, что доступ к связям, которые используются снаружи серивсов не вызывают LazyInitialzationException")
    @Test
    void shouldInsertAndFetchBookWithRelations() {

        var savedBook = bookService.insert("BookTitle_4", testAuthor.getId(), testGenre.getId());

        var optionalBook = bookService.findById(savedBook.getId());
        assertThat(optionalBook).isPresent();

        var book = optionalBook.get();
        assertDoesNotThrow(() -> {
            assertThat(book.getAuthor().getFullName()).isEqualTo(testAuthor.getFullName());
            assertThat(book.getGenre().getName()).isEqualTo(testGenre.getName());
        });
    }

    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    void shouldUpdateBookAndPreserveRelations() {
        var book = bookService.insert("BookTitle_4", testAuthor.getId(), testGenre.getId());

        var updated = bookService.update(book.getId(), "BookTitle_5", testAuthor.getId(), testGenre.getId());

        assertThat(updated.getTitle()).isEqualTo("BookTitle_5");
        assertDoesNotThrow(() -> {
            assertThat(updated.getAuthor().getFullName()).isEqualTo(testAuthor.getFullName());
            assertThat(updated.getGenre().getName()).isEqualTo(testGenre.getName());
        });
    }

}
