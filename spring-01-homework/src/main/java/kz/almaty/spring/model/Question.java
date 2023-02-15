package kz.almaty.spring.model;

public class Question {
    private String number;
    private String correct;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    @Override
    public String toString() {
        return "{" + number + "::" + correct + "::" + question + "::" + option1 + "::" + option2 + "::" + option3 + "::" + option4 + "}";
    }

}
