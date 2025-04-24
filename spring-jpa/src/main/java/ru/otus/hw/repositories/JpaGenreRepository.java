package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaGenreRepository implements GenreRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        var genreTypedQuery = em.createQuery("select g from Genre g", Genre.class);
        return genreTypedQuery.getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        var genre = em.find(Genre.class, id);
        return Optional.ofNullable(genre);
    }

}
