package kz.almaty.spring.model;

import java.util.List;

public class Question {
    private final String text;
    private final List<Option> optionList;

    public Question(String text, List<Option> list) {
        this.text = text;
        this.optionList = list;
    }

    public String getText() {
        return text;
    }

    public List<Option> getOptionList() {
        return optionList;
    }
}
