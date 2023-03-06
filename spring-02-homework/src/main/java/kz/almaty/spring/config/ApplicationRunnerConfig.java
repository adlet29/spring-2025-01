package kz.almaty.spring.config;

import kz.almaty.spring.service.ApplicationRunner;
import kz.almaty.spring.service.QuestionService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationRunnerConfig {
    @Bean
    public ApplicationRunner runner (QuestionService questionService) {
        return new ApplicationRunner(questionService);
    }
}
