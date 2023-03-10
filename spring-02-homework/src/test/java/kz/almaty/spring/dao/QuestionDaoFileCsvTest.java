package kz.almaty.spring.dao;

import kz.almaty.spring.exceptions.FileNameIllegalArgumentException;
import kz.almaty.spring.exceptions.ReaderRuntimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@DisplayName("Класс QuestionDaoFileCsv")
public class QuestionDaoFileCsvTest {
    @DisplayName("Тестовая загрузка файла CSV")
    @Test
    void loadCSVFile() {
        String fileName = "test.csv";
        ClassLoader classLoader = getClass().getClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new FileNameIllegalArgumentException("file not found! " + fileName);
            }
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(streamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            throw new ReaderRuntimeException("problems reading the file!");
        }
    }
}
