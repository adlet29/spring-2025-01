package kz.almaty.spring.service;

import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner {
    private final IOService ioService;

    private final QuestionService questionService;

    public ApplicationRunner(IOService ioService, QuestionService questionService) {
        this.ioService = ioService;
        this.questionService = questionService;
    }

    public void run() {
        ioService.outputString("Run...");
        var questions = questionService.getAll();
        System.out.println(questions.get(0).getText());
    }
}
