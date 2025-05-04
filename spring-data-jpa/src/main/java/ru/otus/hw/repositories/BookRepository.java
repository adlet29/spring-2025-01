package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    @EntityGraph(value = "book-with-author-and-genre", type = EntityGraph.EntityGraphType.FETCH)
    Optional<Book> findById(long id);

    @EntityGraph(value = "book-with-author-and-genre", type = EntityGraph.EntityGraphType.FETCH)
    List<Book> findAll();

    Book save(Book book);

    void deleteById(long id);
}
