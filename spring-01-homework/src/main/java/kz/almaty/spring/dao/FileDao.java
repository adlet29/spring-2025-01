package kz.almaty.spring.dao;

import kz.almaty.spring.domain.File;

public interface FileDao {
    File findByContent(String name);
}
