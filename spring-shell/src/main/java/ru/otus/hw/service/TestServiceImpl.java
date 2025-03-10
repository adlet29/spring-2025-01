package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final LocalizedIOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printLineLocalized("TestService.answer.the.questions");
        ioService.printLine("");

        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question : questions) {
            int correctOption = displayQuestionWithOptions(question);
            int studentOption = getStudentAnswer(question.answers().size());

            testResult.applyAnswer(question, studentOption == correctOption);
        }

        return testResult;
    }

    private int displayQuestionWithOptions(Question question) {
        ioService.printLine(question.text());

        var options = question.answers();
        int correctOption = -1;

        for (int optionNumber = 1; optionNumber <= options.size(); optionNumber++) {
            var option = options.get(optionNumber - 1);
            ioService.printFormattedLine(optionNumber + ") " + option.text());

            if (option.isCorrect()) {
                correctOption = optionNumber;
            }
        }

        return correctOption;
    }

    private int getStudentAnswer(int maxOption) {
        return ioService.readIntForRangeWithPromptLocalized(
                1, maxOption,
                "TestService.select.correct.answer", "TestService.error.index.out.of"
        );
    }

}
