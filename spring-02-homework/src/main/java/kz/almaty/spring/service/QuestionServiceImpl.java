package kz.almaty.spring.service;

import kz.almaty.spring.dao.QuestionDao;
import kz.almaty.spring.model.Question;

import java.util.List;

public class QuestionServiceImpl implements QuestionService {
    private final IOService ioService;
    private final QuestionDao dao;

    public QuestionServiceImpl(IOService ioService, QuestionDao dao) {
        this.ioService = ioService;
        this.dao = dao;
    }

    @Override
    public List<Question> getAll() {
        return dao.findAll();
    }
}
