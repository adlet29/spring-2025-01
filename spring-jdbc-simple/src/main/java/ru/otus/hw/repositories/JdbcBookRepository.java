package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        try {
            Book book = namedParameterJdbcOperations.queryForObject(
                    "SELECT b.id, b.title, b.author_id, a.full_name, b.genre_id, g.name AS genre_name FROM books b " +
                            "JOIN authors a ON b.author_id = a.id " +
                            "JOIN genres g ON b.genre_id = g.id " +
                            "WHERE b.id = :id;\n", params, new BookRowMapper()
            );
            return Optional.ofNullable(book);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> findAll() {
        return namedParameterJdbcOperations.query(
                "SELECT b.id, b.title, b.author_id, a.full_name, b.genre_id, g.name AS genre_name FROM books b " +
                        "JOIN authors a ON b.author_id = a.id " +
                        "JOIN genres g ON b.genre_id = g.id\n", new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        //...
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        //...

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        //...
        // Выбросить EntityNotFoundException если не обновлено ни одной записи в БД
        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            long authorId = rs.getLong("author_id");
            String authorFullName = rs.getString("full_name");
            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            return new Book(id, title, new Author(authorId, authorFullName), new Genre(genreId, genreName));
        }
    }
}
