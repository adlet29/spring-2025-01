package kz.almaty.spring.service;

import java.io.PrintStream;
import java.util.Scanner;

public class IOServiceStreams implements IOService {
    private final PrintStream output;
    private final Scanner input;

    public IOServiceStreams() {
        output = System.out;
        input = new Scanner(System.in);
    }

    @Override
    public void outputString(String s){
        output.println(s);
    }

    @Override
    public void outputStringSimple(String s) {
        output.print(s);
    }

    @Override
    public int readInt(){
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public int readIntWithPrompt(String prompt){
        outputString(prompt);
        return Integer.parseInt(input.nextLine());
    }

    @Override
    public String readStringWithPrompt(String prompt){
        outputString(prompt);
        return input.nextLine();
    }
}
