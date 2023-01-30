package kz.almaty.spring.domain;

import java.util.List;

public class File {
    private final String name;

    private List<List<String>> content;

    public File(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<List<String>> getContent() {
        return content;
    }

    public void setContent(List<List<String>> content) {
        this.content = content;
    }
}
