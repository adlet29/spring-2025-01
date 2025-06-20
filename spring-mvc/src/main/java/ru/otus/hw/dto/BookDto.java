package ru.otus.hw.dto;

import lombok.Data;

@Data
public class BookDto {
    private String title;
    private Long authorId;
    private Long genreId;
}
