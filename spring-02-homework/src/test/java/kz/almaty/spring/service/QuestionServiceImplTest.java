package kz.almaty.spring.service;

import kz.almaty.spring.dao.QuestionDao;
import kz.almaty.spring.dao.QuestionDaoFileCsv;
import kz.almaty.spring.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        questionDao = new QuestionDaoFileCsv("test.csv");
        questionService = new QuestionServiceImpl(questionDao);
    }

    @DisplayName("Получить вопросы теста для студентов")
    @Test
    void getAll() {
        var list = questionService.getAll();
        assertThat(list).isNotEmpty();
        for (Question question : list) {
            assertThat(question.getOptionList()).isNotEmpty();
        }
    }

}
