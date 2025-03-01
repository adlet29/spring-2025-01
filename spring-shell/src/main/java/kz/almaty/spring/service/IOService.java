package kz.almaty.spring.service;

public interface IOService {
    void outputString(String s);
    void outputStringSimple(String s);

    int readInt();

    int readIntWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);

}