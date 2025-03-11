package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcTemplate namedParametersJdbcTemplate;

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);

        try {
            Book book = namedParametersJdbcTemplate.queryForObject(
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
        return namedParametersJdbcTemplate.query(
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
        Map<String, Object> params = Collections.singletonMap("id", id);
        namedParametersJdbcTemplate.update(
                "delete from books where id = :id", params
        );
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();
        namedParametersJdbcTemplate.update(
                "insert into books (title, author_id, genre_id) values (:title, :author_id, :genre_id)",
                new MapSqlParameterSource(Map.of(
                        "title", book.getTitle(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()
                )),
                keyHolder,
                new String[]{"id"}
        );
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        int updatedRows = namedParametersJdbcTemplate.update(
                "update books set title = :title, author_id = :author_id, genre_id = :genre_id where id = :id",
                Map.of(
                        "id", book.getId(),
                        "title", book.getTitle(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId()
                )
        );

        if (updatedRows == 0) {
            throw new EntityNotFoundException("Book with id %d not found".formatted(book.getId()));
        }

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
