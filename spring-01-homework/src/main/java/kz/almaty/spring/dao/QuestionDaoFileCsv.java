package kz.almaty.spring.dao;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import kz.almaty.spring.exceptions.FileNameIllegalArgumentException;
import kz.almaty.spring.model.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class QuestionDaoFileCsv implements QuestionDao {

    private final String name;

    public QuestionDaoFileCsv(String resourceName) {
        this.name = resourceName;
    }

    @Override
    public List<Question> findAll() {

        BufferedReader bufferedReader = getFileFromResourceAsStreamReader(name);
        CSVReader reader = new CSVReader(bufferedReader);

        HeaderColumnNameMappingStrategy<Question> beanStrategy = new HeaderColumnNameMappingStrategy<>();
        beanStrategy.setType(Question.class);

        CsvToBean<Question> csvToBean = new CsvToBean<>();
        csvToBean.setCsvReader(reader);
        csvToBean.setMappingStrategy(beanStrategy);
        List<Question> questions = csvToBean.parse();

        try {
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return questions;
    }

    private BufferedReader getFileFromResourceAsStreamReader(String fileName) {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new FileNameIllegalArgumentException("file not found! " + fileName);
        }

        return new BufferedReader(new InputStreamReader(inputStream));
    }

}
