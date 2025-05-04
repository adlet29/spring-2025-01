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

@DisplayName("Репозиторий на основе Jpa для работы с книгами ")
@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private BookRepository repositoryJpa;

    @Autowired
    private TestEntityManager entityManager;

    private List<Author> dbAuthors;
    private List<Genre> dbGenres;
    private List<Book> dbBooks;
    private List<Comment> dbComments;

    @BeforeEach
    void setUp() {
        dbAuthors = getDbAuthors();
        dbGenres = getDbGenres();
        dbBooks = createBooksWithoutComments(dbAuthors, dbGenres);
        dbComments = createComments(dbBooks);

        dbBooks.forEach(book -> {
            List<Comment> commentsForBook = dbComments.stream()
                    .filter(comment -> comment.getBook().getId() == book.getId())
                    .toList();
            book.setComments(commentsForBook);
        });
    }

    @DisplayName("должен загружать книгу по id")
    @ParameterizedTest
    @MethodSource("getAllBooks")
    void shouldReturnCorrectBookById(Book expectedBook) {
        var actualBook = repositoryJpa.findById(expectedBook.getId());
        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        var actualBooks = repositoryJpa.findAll();
        assertThat(actualBooks).containsExactlyElementsOf(dbBooks);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        var expectedBook = new Book(0, "BookTitle_10500", dbAuthors.get(0), dbGenres.get(0));
        var returnedBook = repositoryJpa.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(entityManager.find(Book.class, returnedBook.getId()))
                .isNotNull()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        var expectedBook = new Book(1L, "BookTitle_10500", dbAuthors.get(2), dbGenres.get(2));

        assertThat(entityManager.find(Book.class, expectedBook.getId()))
                .isNotNull()
                .extracting(Book::getTitle)
                .isNotEqualTo(expectedBook.getTitle());

        var returnedBook = repositoryJpa.save(expectedBook);
        assertThat(returnedBook).isNotNull()
                .matches(book -> book.getId() > 0)
                .usingRecursiveComparison().ignoringExpectedNullFields().isEqualTo(expectedBook);

        assertThat(entityManager.find(Book.class, returnedBook.getId()))
                .isNotNull()
                .isEqualTo(returnedBook);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        assertThat(entityManager.find(Book.class, 1L)).isNotNull();
        repositoryJpa.deleteById(1L);
        assertThat(entityManager.find(Book.class, 1L)).isNull();
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
                .mapToObj(id -> new Comment(id, "comment_" + id, books.get(id - 1)))
                .toList();
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
