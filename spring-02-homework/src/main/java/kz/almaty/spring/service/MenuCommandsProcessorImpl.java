package kz.almaty.spring.service;

import kz.almaty.spring.exceptions.OptionIndexOutOfBoundsException;
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
        int point = 0;
        List<Question> questions = questionService.getAll();
        ioService.outputString("Questions:");
        for (Question question : questions) {
            ioService.outputString(question.getText());
            List<Option> options = question.getOptionList();
            int correctOption = 0;
            for (Option option : options) {
                ioService.outputString(option.getOption() + ") " + option.getText());
                correctOption = option.isTrue() ? option.getOption() : correctOption;
            }
            int userOption = ioService.readIntWithPrompt("Сhoose the correct answer ..");
            checkOptionNumber(userOption, options.size());
            if (userOption == correctOption) {
                point++;
            }
        }
        ioService.outputString("Your result: " + point);
    }

    private static void checkOptionNumber(int optionNumber, int optionCount) {
        if (optionNumber <= 0 || optionNumber > optionCount) {
            throw new OptionIndexOutOfBoundsException("Given number of option is out of range");
        }
    }

}
