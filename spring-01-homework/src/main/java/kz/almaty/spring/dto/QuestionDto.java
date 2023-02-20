package kz.almaty.spring.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class QuestionDto {
    @CsvBindByName(column = "number", required = true)
    private Integer number;
    @CsvBindByName(column = "correct", required = true)
    private String correct;
    @CsvBindByName(column = "question", required = true)
    private String question;
    @CsvBindByName(column = "option1", required = true)
    private String option1;
    @CsvBindByName(column = "option2", required = true)
    private String option2;
    @CsvBindByName(column = "option3", required = true)
    private String option3;
    @CsvBindByName(column = "option4", required = true)
    private String option4;
}
