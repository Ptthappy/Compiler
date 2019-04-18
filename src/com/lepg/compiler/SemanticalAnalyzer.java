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
        this.STOVar = "";
        System.out.println(this.par);

        switch(Compiler.statementType) {  //Maneja el lado izquierdo del statement
            case 0:
                input = input.substring(input.indexOf("a2")); //Omite el tipo de variable
                
            case 2:
                this.STOVar = input.substring(0, input.indexOf("=")).trim();  //Define la variable que se va a almacenar
                input = input.substring(input.indexOf("=") + 1).trim();  //Corta el statement a solo el lado derecho
                break;
                
            case 1:
                return true;
                
            case 3:
                break;
                
            default:
                throw new RuntimeException();
        }
        
        System.out.println(input);
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
        while(this.par > 0) {
            int x0 = 0;
            int x1 = 0;
            this.par--;
            String input2 = input.substring((x0 = input.indexOf(Compiler.Symbol.get(3))) + 1);
            System.out.println(this.par);
            
            if(this.par == 0) {
                input2 = input2.substring(0, (x1 = input2.indexOf(Compiler.Symbol.get(4))) - 1);
                System.out.println(input2);
            } else {
                // a1 + a2 + (a3 + a4 - (a5 * a6)) + 2
                // a1 + a2 + (a3 + a4) - (a5 * a6) + 2
                if (input2.indexOf(Compiler.Symbol.get(3)) < input2.indexOf(Compiler.Symbol.get(4))) { //Anidados
                    input2 = input2.substring(0, (x1 = input2.lastIndexOf(Compiler.Symbol.get(4))) - 1);
                    input2 = resolve(input2);
                } else { //No anidados
                    input2 = input2.substring(0, (x1 = input2.indexOf(Compiler.Symbol.get(4))) - 1);
                }
            }
            x1 += 2;
            input = input.substring(0, x0) + input2 + input.substring(x1);
//            System.out.println("0: " + input.substring(0, x0));
//            System.out.println("1:" + input2);
//            System.out.println("2: " + input.substring(x1));
            System.out.println("input: " + input);
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
