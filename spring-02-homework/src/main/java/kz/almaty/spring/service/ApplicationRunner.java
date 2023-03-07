package kz.almaty.spring.service;

import kz.almaty.spring.exceptions.OptionIndexOutOfBoundsException;

public class ApplicationRunner {

    private final MenuCommandsProcessor commandsProcessor;
    private final IOService ioService;

    public ApplicationRunner(IOService ioService, MenuCommandsProcessor commandsProcessor) {
        this.ioService = ioService;
        this.commandsProcessor = commandsProcessor;
    }

    public void run() {
        commandsProcessor.askLastName();
        commandsProcessor.askFirstName();
        try {
            commandsProcessor.askQuestion();
        } catch (NumberFormatException e) {
            ioService.outputString("Error when entering numbers");
        } catch (OptionIndexOutOfBoundsException e) {
            ioService.outputString("Invalid option number entered");
        }
    }
}
