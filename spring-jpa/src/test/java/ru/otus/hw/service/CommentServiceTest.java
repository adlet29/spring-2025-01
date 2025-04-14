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
import ru.otus.hw.models.Book;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;
import ru.otus.hw.services.CommentServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Интеграционный тест комментариев ")
@DataJpaTest
@Import({CommentServiceImpl.class, JpaBookRepository.class, JpaCommentRepository.class})
@TestPropertySource(properties = "spring.shell.interactive.enabled=false")
class CommentServiceTest {
    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private BookRepository bookRepository;

    private Book testBook;

    @BeforeEach
    void setUp() {
        testBook = bookRepository.findById(1).orElseThrow(() -> new RuntimeException("Book not found with id 1"));
    }

    @DisplayName("Должен сохранить комментарий книге и получить с книгой")
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldInsertAndFetchCommentWithRelations() {
        var savedComment = commentService.insert("comment_4", testBook.getId());
        assertThat(savedComment).isNotNull();
        var loadedComment = commentService.findById(savedComment.getId());
        assertThat(loadedComment).isPresent();
        assertDoesNotThrow(() -> {
            assertThat(loadedComment.get().getBook().getId()).isEqualTo(testBook.getId());
        });
    }

    @DisplayName("Должен обновить комментарий и сохранить связи с книгой")
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldUpdateAndFetchCommentWithRelations() {
        var savedComment = commentService.insert("comment_4", testBook.getId());
        var updated = commentService.update(savedComment.getId(), "comment_5", testBook.getId());
        assertThat(updated).isNotNull();
        assertThat(updated.getText()).isEqualTo("comment_5");
        var loadedComment = commentService.findById(updated.getId());
        assertThat(loadedComment).isPresent();
        assertDoesNotThrow(() -> {
            assertThat(loadedComment.get().getBook().getId()).isEqualTo(testBook.getId());
        });
    }

    @DisplayName("Должен удалить комментарий")
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldDeleteComment() {
        var book = commentService.insert("comment_4", testBook.getId());
        long bookId = book.getId();

        commentService.deleteById(bookId);

        assertThat(commentService.findById(bookId)).isEmpty();
    }

}
