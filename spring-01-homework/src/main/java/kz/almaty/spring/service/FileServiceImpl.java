package kz.almaty.spring.service;

import kz.almaty.spring.dao.FileDao;
import kz.almaty.spring.domain.File;

public class FileServiceImpl implements FileService {
    private final FileDao dao;
    private final String fileName;

    public FileServiceImpl(FileDao dao, String name) {
        this.dao = dao;
        this.fileName = name;
    }

    @Override
    public File generate() {
        return dao.findByContent(fileName);
    }
}
