package kz.almaty.spring.service;

import kz.almaty.spring.dao.QuestionDao;
import kz.almaty.spring.model.Option;
import kz.almaty.spring.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Класс QuestionServiceImpl")
@TestPropertySource(properties = "spring.shell.interactive.enabled=false")
public class QuestionServiceImplTest {

    @MockBean
    private QuestionDao questionDao;

    @Autowired
    private QuestionService questionService;

    @DisplayName("Получить вопросы теста для студентов")
    @Test
    void getAll() {
        List<Question> questions = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        options.add(new Option("Option1", 1, false));
        options.add(new Option("Option2", 2, true));
        options.add(new Option("Option3", 3, false));
        options.add(new Option("Option4", 4, false));
        questions.add(new Question("Question", options));
        when(questionDao.findAll()).thenReturn(questions);
        var list = questionService.getAll();
        assertThat(list).isNotEmpty();
        for (Question question : list) {
            assertThat(question.getOptionList()).isNotEmpty();
        }
        verify(questionDao, times(1)).findAll();
    }

}
