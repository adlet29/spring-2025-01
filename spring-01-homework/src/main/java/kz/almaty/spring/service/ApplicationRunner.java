package kz.almaty.spring.service;

public class ApplicationRunner {

    private final QuestionService questionService;

    public ApplicationRunner(QuestionService service) {
        this.questionService = service;
    }

    public void run() {
        questionService.showQuestions();
    }

}
