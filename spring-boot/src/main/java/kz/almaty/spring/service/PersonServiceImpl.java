package kz.almaty.spring.service;

import kz.almaty.spring.model.Person;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {
    private final IOService ioService;

    public PersonServiceImpl(IOService ioService) {
        this.ioService = ioService;
    }

    @Override
    public Person getCurrentPerson() {
        String lastName = ioService.readStringWithPrompt("Enter last name...");
        String firstName = ioService.readStringWithPrompt("Enter first name...");
        return new Person(firstName, lastName);
    }
}
