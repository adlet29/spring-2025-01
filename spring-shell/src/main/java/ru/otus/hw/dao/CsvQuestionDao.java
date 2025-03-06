package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        var fileName = fileNameProvider.getTestFileName();
        try (BufferedReader bufferedReader = getFileFromResourceAsStreamReader(fileName)) {
            var csvToBean = getCsvToBean(bufferedReader);
            var questionDtoList = csvToBean.parse();
            List<Question> questions = new ArrayList<>();
            for (QuestionDto questionDto : questionDtoList) {
                Question question = questionDto.toDomainObject();
                questions.add(question);
            }
            return questions;
        } catch (IOException ex) {
            throw new QuestionReadException("problems reading the file!", ex);
        }
    }

    private BufferedReader getFileFromResourceAsStreamReader(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new QuestionReadException("file not found! " + fileName);
        }
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        return new BufferedReader(inputStreamReader);
    }

    private CsvToBean<QuestionDto> getCsvToBean(BufferedReader bufferedReader) {
        return new CsvToBeanBuilder<QuestionDto>(bufferedReader)
                .withType(QuestionDto.class)
                .withIgnoreLeadingWhiteSpace(true)
                .withSkipLines(1)
                .withSeparator(';')
                .build();
    }

}
