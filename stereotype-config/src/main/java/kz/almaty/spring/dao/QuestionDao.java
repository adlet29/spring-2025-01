package kz.almaty.spring.dao;

import kz.almaty.spring.model.Question;

import java.util.List;

public interface QuestionDao {
    List<Question> findAll();
}
