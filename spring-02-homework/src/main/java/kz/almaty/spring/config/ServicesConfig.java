package kz.almaty.spring.config;

import kz.almaty.spring.dao.QuestionDao;
import kz.almaty.spring.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.io.PrintStream;

@Configuration
public class ServicesConfig {

    @Bean
    public IOService ioService(@Value("#{ T(java.lang.System).out }") PrintStream outputStream,
                               @Value("#{ T(java.lang.System).in }")InputStream inputStream) {
        return new IOServiceStreams(outputStream, inputStream);
    }

    @Bean
    public QuestionService questionService(QuestionDao questionDao, IOService ioService) {
       return new QuestionServiceImpl(ioService, questionDao);
    }

    @Bean
    public MenuCommandsProcessor menuCommandsProcessor(IOService ioService, QuestionService questionService) {
        return new MenuCommandsProcessorImpl(ioService, questionService);
    }

}
