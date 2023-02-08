package kz.almaty.spring.service;

import kz.almaty.spring.dao.QuestionDao;
import kz.almaty.spring.model.Answer;
import kz.almaty.spring.model.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao dao;

    public QuestionServiceImpl(QuestionDao dao) {
        this.dao = dao;
    }

    @Override
    public void showQuestions() {
        List<Question> questionList =dao.findAll();
        for (Question question : questionList) {
            System.out.println(question.getText());
            for (Answer answer : question.getAnswerList()) {
                System.out.print(answer.getText()+ " ");
            }
            System.out.println();
        }
    }
}
