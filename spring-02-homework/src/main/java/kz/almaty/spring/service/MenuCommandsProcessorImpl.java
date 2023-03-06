package kz.almaty.spring.service;

import kz.almaty.spring.model.Option;
import kz.almaty.spring.model.Question;

import java.util.List;

public class MenuCommandsProcessorImpl implements MenuCommandsProcessor {
    private final IOService ioService;
    private  final QuestionService questionService;
    public MenuCommandsProcessorImpl(IOService ioService, QuestionService questionService) {
        this.ioService = ioService;
        this.questionService = questionService;
    }

    @Override
    public void askFirstName() {
        ioService.readStringWithPrompt("Enter first name...");
    }

    @Override
    public void askLastName() {
        ioService.readStringWithPrompt("Enter last name...");
    }

    @Override
    public void askQuestion() {
        List<Question> questions = questionService.getAll();
        ioService.outputString("Questions:");
        for (Question question : questions) {
            ioService.outputString(question.getText());
            this.showOptions(question.getOptionList());
        }
    }

    private void showOptions(List<Option> options) {
        /*
        String correct = "";
        for (Option option : options) {
            ioService.outputString(option.getOption() + ") " + option.getText());
            correct = option.getIsTrue() ? option.getOption() : "";
        }
        String output = ioService.readStringWithPrompt("Choose the correct answer...");
         */
    }
}
