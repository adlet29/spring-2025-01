package ru.otus.hw.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
@NamedEntityGraph(name = "comment-with-book", attributeNodes = {
        @NamedAttributeNode("book")
})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "text", nullable = false)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

}
