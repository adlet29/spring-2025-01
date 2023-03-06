package kz.almaty.spring.service;

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
        commandsProcessor.askQuestion();
    }

}
