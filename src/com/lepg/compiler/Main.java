package com.lepg.compiler;

import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * @author Ptthappy
 */


public class Main {
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Compiler compiler = null;
        try { compiler = new Compiler(); } catch (FileNotFoundException e) { e.printStackTrace(); System.exit(0); }
        
        String input = "";
        System.out.println("Enter sentences. The analyzer will check whether the input is valid or not.");
        System.out.println("Enter \"exit\" to end the program.");
        do {
            try {
                input = in.nextLine();
                if (input.trim().equals("exit")) break;
                System.out.println(compiler.compile(input));
                System.out.println();
            } catch (Exception e) {
                System.err.println("false");
            }
        } while(true);
        
    }
    
    
}
