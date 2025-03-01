package kz.almaty.spring.service;

import kz.almaty.spring.exceptions.OptionIndexOutOfBoundsException;
import kz.almaty.spring.model.Option;
import kz.almaty.spring.model.Person;
import kz.almaty.spring.model.Question;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.List;
import java.util.Locale;

@ShellComponent
public class ApplicationRunner {
    private final IOService ioService;

    private final PersonService personService;

    private final QuestionService questionService;

    private final MessageSource messageSource;

    private final String language;


    public ApplicationRunner(IOService ioService, PersonService personService, QuestionService questionService,
                             MessageSource messageSource, @Value("${language}") String language) {
        this.ioService = ioService;
        this.personService = personService;
        this.questionService = questionService;
        this.messageSource = messageSource;
        this.language = language;
    }

    @ShellMethod(key = "start-test", value = "start testing")
    public void run() {
        Person person = personService.getCurrentPerson();
        try {
            int point = this.askQuestions();
            ioService.outputString(person.getFirstName() + " " + person.getLastName());
            ioService.outputString(getMessage("your.result") + " " + point);
        } catch (NumberFormatException e) {
            ioService.outputString(getMessage("error.number.format"));
        } catch (OptionIndexOutOfBoundsException e) {
            ioService.outputString(getMessage("error.index.out.of"));
        }
    }

    private int askQuestions() {
        int point = 0;
        List<Question> questions = questionService.getAll();
        for (Question question : questions) {
            ioService.outputString(question.getText());
            List<Option> options = question.getOptionList();
            int correctOption = 0;
            for (Option option : options) {
                ioService.outputString(option.getOption() + ") " + option.getText());
                correctOption = option.isTrue() ? option.getOption() : correctOption;
            }
            int userOption = ioService.readIntWithPrompt(getMessage("select.correct.answer"));
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

    private String getMessage(String code) {
        return messageSource.getMessage(code, new Object[]{}, new Locale(language));
    }

}
