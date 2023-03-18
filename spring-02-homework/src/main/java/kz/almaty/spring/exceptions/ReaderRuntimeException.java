package kz.almaty.spring.exceptions;

import java.io.IOException;

public class ReaderRuntimeException extends RuntimeException {
    public ReaderRuntimeException(String s, IOException e) {
        super(s, e);
    }
}
