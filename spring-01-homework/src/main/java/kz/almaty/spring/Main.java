package kz.almaty.spring;

import kz.almaty.spring.service.ApplicationRunner;
import kz.almaty.spring.service.FileService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        FileService fileService = context.getBean(FileService.class);
        new ApplicationRunner(fileService).run();
    }

}
