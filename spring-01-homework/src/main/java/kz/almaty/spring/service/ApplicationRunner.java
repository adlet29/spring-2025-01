package kz.almaty.spring.service;

import kz.almaty.spring.domain.File;
import java.util.List;

public class ApplicationRunner {

    private final FileService fileService;

    public ApplicationRunner(FileService fileService) {
        this.fileService = fileService;
    }

    public void run() {
        File storage = fileService.generate();
        List<List<String>> listList = storage.getContent();
        System.out.println("file size: " + listList.size());
        for (List<String> list : listList) {
            for (String value : list) {
                System.out.println(value);
            }
        }
    }

}
