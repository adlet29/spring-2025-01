package ru.otus.hw.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.hw.service.TestRunnerServiceImpl;

@ShellComponent
@RequiredArgsConstructor
public class TestRunnerShell {

    private final TestRunnerServiceImpl testRunnerService;

    @ShellMethod(key = "run-test", value = "Запуск теста для студента")
    public void runTest() {
        testRunnerService.run();
    }

}
