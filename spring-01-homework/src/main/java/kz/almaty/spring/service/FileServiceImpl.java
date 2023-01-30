package kz.almaty.spring.service;

import kz.almaty.spring.dao.FileDao;
import kz.almaty.spring.domain.File;

public class FileServiceImpl implements FileService {
    private final FileDao dao;

    public FileServiceImpl(FileDao dao) {
        this.dao = dao;
    }

    @Override
    public File generate(String name) {
        return dao.findByContent(name);
    }
}
