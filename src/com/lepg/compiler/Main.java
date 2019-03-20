package com.lepg.compiler;

import java.util.Scanner;

/**
 * @author Ptthappy
 */


public class Main {
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Compiler compiler = new Compiler();
        String input = "";
        System.out.println("Enter sentences. The analyzer will check whether the input is valid or not.");
        System.out.println("Enter \"exit\" to end the program.");
        do {
            input = in.nextLine();
            if (input.trim().equals("exit")) break;
            System.out.println(compiler.compile(input));
            System.out.println();
        } while(true);
        
    }
    
    
}
