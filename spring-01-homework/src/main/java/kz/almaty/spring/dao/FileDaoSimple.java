package kz.almaty.spring.dao;

import kz.almaty.spring.domain.File;
import kz.almaty.spring.util.ScannerUtil;

import java.io.IOException;
import java.util.List;

public class FileDaoSimple implements FileDao {
    @Override
    public File findByContent(String name) {
        File file = new File(name);
        ScannerUtil scannerUtil = new ScannerUtil();
        try {
            List<List<String>> list = scannerUtil.getRecords(name);
            file.setContent(list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return file;
    }
}
