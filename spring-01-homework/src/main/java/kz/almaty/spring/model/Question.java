package kz.almaty.spring.model;

import lombok.Data;
import java.util.List;

@Data
public class Question {
    private final String text;
    private final List<Option> optionList;

    public Question(String text, List<Option> list) {
        this.text = text;
        this.optionList = list;
    }

}
