package ru.otus.hw.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.JpaAuthorRepository;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaGenreRepository;
import ru.otus.hw.services.BookServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Интеграционный тест книг ")
@DataJpaTest
@Import({BookServiceImpl.class, JpaBookRepository.class, JpaAuthorRepository.class, JpaGenreRepository.class})
@TestPropertySource(properties = "spring.shell.interactive.enabled=false")
class BookServiceTest {
    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private JpaAuthorRepository authorRepository;

    @Autowired
    private JpaGenreRepository genreRepository;

    private Author testAuthor;

    private Genre testGenre;

    @BeforeEach
    void setUp() {
        testAuthor = authorRepository.findById(1).orElseThrow(() -> new RuntimeException("Author not found with id 1"));
        testGenre = genreRepository.findById(1).orElseThrow(() -> new RuntimeException("Genre not found with id 1"));
    }


    @DisplayName("Должен сохранить книгу и получить её с связанным автором и жанром")
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
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

    @DisplayName("Должен обновить название книги и сохранить связи с автором и жанром")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldUpdateBookAndPreserveRelations() {
        var book = bookService.insert("BookTitle_4", testAuthor.getId(), testGenre.getId());

        var updated = bookService.update(book.getId(), "BookTitle_5", testAuthor.getId(), testGenre.getId());

        assertThat(updated.getTitle()).isEqualTo("BookTitle_5");
        assertDoesNotThrow(() -> {
            assertThat(updated.getAuthor().getFullName()).isEqualTo(testAuthor.getFullName());
            assertThat(updated.getGenre().getName()).isEqualTo(testGenre.getName());
        });
    }

    @DisplayName("Должен удалить книгу")
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldDeleteBook() {
        var book = bookService.insert("BookTitle_4", testAuthor.getId(), testGenre.getId());
        long id = book.getId();

        bookService.deleteById(id);

        assertThat(bookService.findById(id)).isEmpty();
    }

}
