package com.lepg.compiler;

import java.util.Scanner;

/**
 * @author Ptthappy
 */


public class Main {
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Compiler compiler = null;
        try { compiler = new Compiler(); } catch (Exception e) { e.printStackTrace(); System.exit(0); }
        
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
                e.printStackTrace();
                System.err.println("false");
            }
        } while(true);
        compiler.close();
    }
    
    
}

        /**
         * ESTO SE HACE ARRIBA****
         * a1 + a2 - a3
         * -> a2a1+ - a3
         * -> a3a2a1+-
         * a3a2a1+-
         * 
         * 
         * Esto se hace en codeGenerator
         * -> ADD a3 a2 x1
         * -> SUB x1 a1 x2
         *  - Verifica que ya no queden términos (true) -
         * -> return (porque no se almacenó ninguna variable)
         * 
         * a1 = a2 + a3 - a4
         * -> a2 + a3 - a4 (separa a1 del string completo y cuando termine el procedimiento vuelve a salir xd)
         * ...
         * -> ADD a3 a2 x1
         * -> SUB x1 a1 x2 
         * -> STO x2 a1
         * 
        */
        //Generar el string
        // a5a4a3a2a1+++-
        