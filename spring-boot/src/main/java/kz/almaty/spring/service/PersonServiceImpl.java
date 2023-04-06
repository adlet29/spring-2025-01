package kz.almaty.spring.service;

import kz.almaty.spring.model.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class PersonServiceImpl implements PersonService {
    private final IOService ioService;

    private final MessageSource messageSource;

    private final String language;

    public PersonServiceImpl(IOService ioService, MessageSource messageSource, @Value("${language}") String language) {
        this.ioService = ioService;
        this.messageSource = messageSource;
        this.language = language;
    }

    @Override
    public Person getCurrentPerson() {
        String askLastName = messageSource.getMessage("ask.last.name", new Object[]{}, new Locale(language));
        String askFirstName = messageSource.getMessage("ask.first.name", new Object[]{}, new Locale(language));
        String lastName = ioService.readStringWithPrompt(askLastName);
        String firstName = ioService.readStringWithPrompt(askFirstName);
        return new Person(firstName, lastName);
    }
}
