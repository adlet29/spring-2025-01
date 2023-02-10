package kz.almaty.spring.dao;

import kz.almaty.spring.model.Answer;
import kz.almaty.spring.model.Question;
import kz.almaty.spring.util.ScannerUtil;
import kz.almaty.spring.exceptions.ScannerIndexOutOfBoundsException;
import kz.almaty.spring.exceptions.ScannerNullPointerException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoFileCsv implements QuestionDao {
    private List<Question> questionList;

    public QuestionDaoFileCsv(String resourceName) {
        try {
            this.read(resourceName);
        } catch (ScannerNullPointerException e) {
            System.out.println("Content of csv file is empty");
        } catch (ScannerIndexOutOfBoundsException e) {
            System.out.println("Invalid csv file");
        }
    }

    @Override
    public List<Question> findAll() {
        return questionList;
    }

    private void read(String name) {
        questionList = new ArrayList<>();
        ScannerUtil scannerUtil = new ScannerUtil();
        try {
            List<List<String>> listList = scannerUtil.getRecords(name);
            listList.remove(0);
            int j = 0;
            for (List<String> list : listList) {
                List<Answer> answerList = new ArrayList<>();
                for (String value : list) {
                    String[] arr = value.split(";");
                    if (j + 1 == Integer.parseInt(arr[0])) {
                        for (int i = 0; i < arr.length; i++) {
                            if (i > 1) {
                                Answer answer = new Answer(arr[i]);
                                answerList.add(answer);
                            }
                        }
                        Question question = new Question(arr[1], answerList);
                        questionList.add(question);
                    } else {
                        throw new ScannerIndexOutOfBoundsException("Given file item index is out of range");
                    }
                }
                j++;
            }
            if (questionList.isEmpty()) {
                throw new ScannerNullPointerException("File content is empty");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
