package com.lepg.compiler;

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Stack;

/**
 * @author Ptthappy
 */


public class SemanticalAnalyzer {
    private Stack<String> stack = null;
    private FileOutputStream out;
    protected int par;
    private String STOVar = "";
    
    SemanticalAnalyzer() throws FileNotFoundException {
        if (!(new File("C:\\CompilerOutput").exists())) {
            System.out.println("Folder doesn't exist. Creating Folder");
            
            if(new File("C:\\CompilerOutput").mkdir()) {
                System.out.println("Folder created");
            } else {
                System.err.println("Couldn't create folder");
                System.exit(0);
            }
        }
        if (!(new File("C:\\CompilerOutput\\output.txt").exists())) {
                System.out.println("File does't exist. Creating file...");
                try {
                    new File("C:\\CompilerOutput\\output.txt").createNewFile();
                    System.out.println("File created");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.err.println("Couldn't create file");
                    System.exit(0);
                }
            }
        
        out = new FileOutputStream("C:\\CompilerOutput\\output.txt");
        stack = new Stack<>();
    }
    
    public void testCodeGenerator(String input) {
        generateCode(input);
    }
    
    public boolean analyze(String input) {
        System.out.println();
        input = input.substring(0, input.length() - 1).trim();  // EPA CIUDADANO -> Quitar el $
        this.par = Compiler.par;
        System.out.println(this.par);
//        String[] in = input.split(" ");
        String in = "";
        if ((in = resolve(input)).equals("")) {
            System.out.println("Le cayó mierda al abanico");
            return false;
        } else {
            generateCode(in);
            return true;
        }
    }
    
    protected String resolve(String input) {
        String output = "";
        if(this.par > 0) {
            this.par--;
            String input2 = input.substring(input.indexOf(Compiler.Symbol.get(3)) + 1);
            System.out.println(this.par);
            
            if(this.par == 0) {
                input2 = input2.substring(0, input2.indexOf(Compiler.Symbol.get(4)) - 1);
            } else {
                // Rectificar los paréntesis y asignarlos a input2
                System.out.println("felisidades eres gei");
            }
            //Llamarse recursivamente hasta que no queden paréntesis
            System.out.println(input2);
            
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
        
        return output;
    }
    
    protected boolean generateCode(String string) {
        //Generar el código ASM y pasarlo a un archivo .txt
        String operands = "";
        String operators = "";
        for (int i = 0; i < string.length(); i++) {
            if( Compiler.Operator.contains(((Character)string.charAt(i)).toString()) ) {
                operands = string.substring(0, i);
                operators = string.substring(i, string.length());
                break;
            }
            
        }
        System.out.println("Operands: " + operands + "\nOperators: " + operators);
        return false;
    }
    
//    2 + 5 * 3
//    3 5 2 + *
//    
//    ADD a1 a2 x1
//    MUL x a3 x2
//    STO x2
    
}
