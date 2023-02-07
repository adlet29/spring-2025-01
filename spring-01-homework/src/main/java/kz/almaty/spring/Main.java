package kz.almaty.spring;


import kz.almaty.spring.domain.File;
import kz.almaty.spring.service.FileService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        FileService service = context.getBean(FileService.class);
        File storage = service.generate();
        List<List<String>> listList = storage.getContent();
        System.out.println("file size: " + listList.size());
        for (List<String> list : listList) {
            for (String value : list) {
                System.out.println(value);
            }
        }

    }
}
