package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public Optional<Comment> findById(long id) {
        EntityGraph<?> graph = em.getEntityGraph("comment-with-book");
        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);
        var comment = em.find(Comment.class, id, hints);
        return Optional.ofNullable(comment);
    }

    @Override
    public List<Comment> findByBookId(long bookId) {
        var bookTypedQuery = em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class);
        bookTypedQuery.setParameter("bookId", bookId);
        return bookTypedQuery.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            em.persist(comment);
            return comment;
        }
        return em.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        var comment = em.find(Comment.class, id);
        if (comment == null) {
            throw new EntityNotFoundException("Comment with id %d not found".formatted(id));
        }
        em.remove(comment);
    }

}
