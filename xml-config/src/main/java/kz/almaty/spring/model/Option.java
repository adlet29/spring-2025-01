package kz.almaty.spring.model;

import lombok.Data;

@Data
public class Option {
    private final String text;
    private final String option;
    private final Boolean isTrue;

    public Option(String text, String option, boolean isTrue) {
        this.text = text;
        this.option = option;
        this.isTrue = isTrue;
    }

}
