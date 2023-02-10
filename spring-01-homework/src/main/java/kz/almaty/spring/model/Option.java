package kz.almaty.spring.model;

public class Option {
    private final String text;
    private final String option;
    private final boolean isTrue;

    public Option(String text, String option, boolean isTrue) {
        this.text = text;
        this.option = option;
        this.isTrue = isTrue;
    }

    public String getText() {
        return text;
    }

    public String getOption() {
        return option;
    }
}
