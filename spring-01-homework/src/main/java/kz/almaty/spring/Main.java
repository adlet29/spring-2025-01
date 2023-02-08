package kz.almaty.spring;

import kz.almaty.spring.service.ApplicationRunner;
import kz.almaty.spring.service.QuestionService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionService questionService = context.getBean(QuestionService.class);
        new ApplicationRunner(questionService).run();
    }

}
