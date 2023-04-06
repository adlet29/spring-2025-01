package kz.almaty.spring.service;

import jakarta.annotation.PostConstruct;
import kz.almaty.spring.exceptions.OptionIndexOutOfBoundsException;
import kz.almaty.spring.model.Option;
import kz.almaty.spring.model.Person;
import kz.almaty.spring.model.Question;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicationRunner {
    private final IOService ioService;

    private final PersonService personService;

    private final QuestionService questionService;


    public ApplicationRunner(IOService ioService, PersonService personService, QuestionService questionService) {
        this.ioService = ioService;
        this.personService = personService;
        this.questionService = questionService;
    }
    @PostConstruct
    public void run() {
        Person person = personService.getCurrentPerson();
        try {
            int point = this.askQuestions();
            ioService.outputString(person.getFirstName() + " " + person.getLastName());
            ioService.outputString("Your result:" + " " + point);
        } catch (NumberFormatException e) {
            ioService.outputString("Error when entering numbers");
        } catch (OptionIndexOutOfBoundsException e) {
            ioService.outputString("Invalid option number entered");
        }
    }

    private int askQuestions() {
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
        return point;
    }

    private static void checkOptionNumber(int optionNumber, int optionCount) {
        if (optionNumber <= 0 || optionNumber > optionCount) {
            throw new OptionIndexOutOfBoundsException("Given number of option is out of range");
        }
    }

}
