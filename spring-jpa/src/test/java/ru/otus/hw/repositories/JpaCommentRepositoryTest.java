package ru.otus.hw.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями ")
@DataJpaTest
@Import({JpaCommentRepository.class})
class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository repositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    private List<Book> dbBooks;

    @BeforeEach
    void setUp() {
        var dbAuthors = getDbAuthors();
        var dbGenres = getDbGenres();
        dbBooks = createBooksWithoutComments(dbAuthors, dbGenres);
    }

    @DisplayName("должен загружать комментарий по id")
    @ParameterizedTest
    @MethodSource("getAllComments")
    void shouldReturnCorrectCommentById(Comment expectedComment) {
        var actualComment = repositoryJpa.findById(expectedComment.getId());
        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать список всех комментариев по id книги")
    @ParameterizedTest
    @MethodSource("getAllBooks")
    void shouldReturnCorrectCommentsList(Book expectedBook) {
        var actualComments = repositoryJpa.findByBookId(expectedBook.getId());
        assertThat(actualComments).containsExactlyElementsOf(expectedBook.getComments());
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        var expectedComment = new Comment(0, "Comment_10500", dbBooks.get(0));
        var returnedComment = repositoryJpa.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(entityManager.find(Comment.class, returnedComment.getId()))
                .isNotNull()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        var expectedComment = new Comment(1L, "Comment_10500", dbBooks.get(0));
        assertThat(entityManager.find(Comment.class, expectedComment.getId()))
                .isNotNull()
                .extracting(Comment::getText)
                .isNotEqualTo(expectedComment.getText());

        var returnedComment = repositoryJpa.save(expectedComment);
        assertThat(returnedComment).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedComment);

        assertThat(entityManager.find(Comment.class, returnedComment.getId()))
                .isNotNull()
                .isEqualTo(returnedComment);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertThat(entityManager.find(Comment.class, 1L)).isNotNull();
        repositoryJpa.deleteById(1L);
        assertThat(entityManager.find(Comment.class, 1L)).isNull();
    }

    private static List<Author> getDbAuthors() {
        return IntStream.range(1, 4)
                .mapToObj(id -> new Author(id, "Author_" + id))
                .toList();
    }

    private static List<Genre> getDbGenres() {
        return IntStream.range(1, 4)
                .mapToObj(id -> new Genre(id, "Genre_" + id))
                .toList();
    }

    private static List<Book> createBooksWithoutComments(List<Author> authors, List<Genre> genres) {
        return IntStream.range(1, 4)
                .mapToObj(id -> new Book((long) id, "BookTitle_" + id, authors.get(id - 1), genres.get(id - 1)))
                .toList();
    }

    private static List<Comment> createComments(List<Book> books) {
        return IntStream.range(1, 4)
                .mapToObj(id -> new Comment((long) id, "comment_" + id, books.get(id - 1)))
                .toList();
    }

    private static List<Comment> getAllComments() {
        var authors = getDbAuthors();
        var genres = getDbGenres();
        var books = createBooksWithoutComments(authors, genres);

        return createComments(books);
    }

    private static List<Book> getAllBooks() {
        var authors = getDbAuthors();
        var genres = getDbGenres();
        var books = createBooksWithoutComments(authors, genres);
        var comments = createComments(books);

        books.forEach(book -> {
            List<Comment> commentsForBook = comments.stream()
                    .filter(comment -> comment.getBook().getId() == book.getId())
                    .toList();
            book.setComments(commentsForBook);
        });

        return books;
    }

}
