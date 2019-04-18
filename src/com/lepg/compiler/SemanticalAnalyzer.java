package com.lepg.compiler;

import java.io.OutputStream;
import java.util.Stack;

/**
 * @author Ptthappy
 */


public class SemanticalAnalyzer {
    private Stack<String> stack = null;
    protected int par;
    
    SemanticalAnalyzer() {
        //Asignarle el outputString a un .txt que va a utilizar y sobreescribir
        //el codeGenerator y asignarle la fecha como cabecera
        stack = new Stack<>();
    }
    
    public boolean analyze(String input) {
        System.out.println();
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
        //Generar el string
        return output;
    }
    
    protected boolean generateCode(String string) {
        //Generar el código ASM y pasarlo a un archivo .txt
        return false;
    }
    
//    2 + 5 * 3
//    3 5 2 + *
//    
//    ADD a1 a2 x1
//    MUL x a3 x2
//    STO x2
    
}
