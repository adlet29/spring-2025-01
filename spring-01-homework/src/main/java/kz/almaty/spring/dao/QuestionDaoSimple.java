package kz.almaty.spring.dao;

import kz.almaty.spring.model.Answer;
import kz.almaty.spring.model.Question;
import kz.almaty.spring.util.ScannerUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoSimple implements QuestionDao {
    private final List<Question> questionList = new ArrayList<>();

    public QuestionDaoSimple(String resourceName) {
        this.read(resourceName);
    }

    @Override
    public List<Question> findAll() {
        return questionList;
    }

    private void read(String name) {
        ScannerUtil scannerUtil = new ScannerUtil();
        try {
            List<List<String>> listList = scannerUtil.getRecords(name);
            if (listList.size() > 0) {
                listList.remove(0);
                for (List<String> list : listList) {
                    List<Answer> answerList = new ArrayList<>();
                    for (String value : list) {
                        String[] arr = value.split(";");
                        for (int i = 0; i < arr.length; i++) {
                            if (i > 1) {
                                Answer answer = new Answer(arr[i]);
                                answerList.add(answer);
                            }
                        }
                        Question question = new Question(arr[1], answerList);
                        questionList.add(question);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
