package kz.almaty.spring.config;

import kz.almaty.spring.dao.QuestionDao;
import kz.almaty.spring.dao.QuestionDaoFileCsv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:application.properties")
@Configuration
public class DaoConfig {

    @Bean
    public QuestionDao questionDao(@Value("${question.file.name}") String questionFileName ) {
        System.out.println(questionFileName);
        return new QuestionDaoFileCsv(questionFileName);
    }

}
