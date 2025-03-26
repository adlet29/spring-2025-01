package ru.otus.hw.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CommentConverter {

    public String commentToString(Comment comment) {
        return "Id: %d, comment: %s".formatted(
                comment.getId(),
                comment.getText());
    }

    public String commentsToString(List<Comment> comments) {
        if (comments == null || comments.isEmpty()) {
            return "No comments";
        }
        return comments.stream().map(this::commentToString).collect(Collectors.joining("\n"));
    }

}
