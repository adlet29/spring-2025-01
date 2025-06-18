package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.controllers.CommentController;
import ru.otus.hw.services.CommentService;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Тестирование контроллер для CRUD операций над комментариями")
@WebMvcTest(CommentController.class)
class CommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("отображение страницы со списком комментариев")
    void testListPage() throws Exception {
        mockMvc.perform(get("/comment").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment"))
                .andExpect(model().attributeExists("comments"));
        verify(commentService).findByBookId(1L);
    }

}
