package kz.almaty.spring.model;

import lombok.Data;

@Data
public class Option {
    private final String text;
    private final int option;
    private final boolean isTrue;

    public Option(String text, int option, boolean isTrue) {
        this.text = text;
        this.option = option;
        this.isTrue = isTrue;
    }

}
