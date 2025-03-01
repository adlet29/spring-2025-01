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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayName("Класс QuestionServiceImpl")
@ExtendWith(MockitoExtension.class)
public class QuestionServiceImplTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @BeforeEach
    void setUp() {
        List<Question> questions = new ArrayList<>();
        List<Option> options = new ArrayList<>();
        options.add(new Option("Option1", 1, false));
        options.add(new Option("Option2", 2, true));
        options.add(new Option("Option3", 3, false));
        options.add(new Option("Option4", 4, false));
        questions.add(new Question("Question", options));
        when(questionDao.findAll()).thenReturn(questions);
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
