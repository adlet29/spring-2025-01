package kz.almaty.spring.service;

import kz.almaty.spring.domain.File;

import java.io.IOException;

public interface FileService {
    File generate() throws IOException;
}
