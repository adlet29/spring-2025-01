package kz.almaty.spring.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class QuestionDto {
    @CsvBindByName(column = "number", required = true)
    private int number;
    @CsvBindByName(column = "correct", required = true)
    private int correct;
    @CsvBindByName(column = "question", required = true)
    private String question;
    @CsvBindByName(column = "option1")
    private String option1;
    @CsvBindByName(column = "option2")
    private String option2;
    @CsvBindByName(column = "option3")
    private String option3;
    @CsvBindByName(column = "option4")
    private String option4;
}
