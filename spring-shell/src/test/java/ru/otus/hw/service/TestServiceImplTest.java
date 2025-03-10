package ru.otus.hw.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Класс TestServiceImplTest")
@TestPropertySource(properties = "spring.shell.interactive.enabled=false")
class TestServiceImplTest {

    @MockitoBean
    private LocalizedIOService ioService;
    @MockitoBean
    private QuestionDao questionDao;
    @Autowired
    private TestService testService;

    @DisplayName("Получить вопросы теста для студентов")
    @Test
    void testExecuteTestFor() {
        List<Question> questions = List.of(
                new Question("Is there life on Mars?", List.of(
                        new Answer("Science doesn't know this yet", true),
                        new Answer("Certainly. The red UFO is from Mars. And green is from Venus", false),
                        new Answer("Absolutely not", false)
                ))
        );
        when(questionDao.findAll()).thenReturn(questions);
        when(ioService.readIntForRangeWithPromptLocalized(anyInt(), anyInt(), anyString(), anyString())).thenReturn(1);

        Student student = new Student("Test", "Test");
        TestResult testResult = testService.executeTestFor(student);
        assertNotNull(testResult);
        assertEquals(student, testResult.getStudent());
        assertEquals(1, testResult.getRightAnswersCount());
        verify(questionDao, times(1)).findAll();
    }

}
