package kz.almaty.spring.dao;

import kz.almaty.spring.model.Question;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Класс QuestionDaoFileCsv")
public class QuestionDaoFileCsvTest {

    @DisplayName("Тестовая загрузка файла CSV")
    @Test
    void findAll() {
        QuestionDaoFileCsv questionDaoFileCsv = new QuestionDaoFileCsv("test_en-test.csv");
        var questions = questionDaoFileCsv.findAll();
        assertThat(questions).isNotEmpty();
        for (Question question : questions) {
            assertThat(question.getOptionList()).isNotEmpty();
        }
    }

}
