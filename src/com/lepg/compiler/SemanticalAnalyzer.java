package com.lepg.compiler;

import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author Ptthappy
 */


public class SemanticalAnalyzer {
    private Stack<String> stack = null;
    private FileOutputStream out;
    protected int par;
    private String STOVar = "";
    private String res = "";
    
    SemanticalAnalyzer() throws FileNotFoundException, IOException {
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
        Date date = new Date();
        out = new FileOutputStream("C:\\CompilerOutput\\output.txt", true);
        out.write((date.getDate() + "/" + (date.getMonth() + 1) + "/" + (date.getYear() + 
                1900) + ": " + date.getHours() + ":" + 
                (date.getMinutes() > 9 ? date.getMinutes() : "0" + date.getMinutes()) ).getBytes());
        out.write("\n\n".getBytes());
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
        this.res = "";
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
                if (input2.indexOf(Compiler.Symbol.get(3)) < input2.indexOf(Compiler.Symbol.get(4))) { //Anidados
                    input2 = input2.substring(0, (x1 = input2.lastIndexOf(Compiler.Symbol.get(4))) - 1);
                    input2 = resolve(input2);
                } else { //No anidados
                    input2 = input2.substring(0, (x1 = input2.indexOf(Compiler.Symbol.get(4))) - 1);
                }
            }
            x1 += 2;
            input = input.substring(0, x0) + input2 + input.substring(x1);
            System.out.println("input: " + input);
        }
        
        String[] split = input.split(" ");
        List<String> mid = Arrays.asList(split);
        ArrayList<String> tokens = new ArrayList<>();
        tokens.addAll(mid);

        int index;

        String token = "";
        while(!tokens.isEmpty()){
            token = tokens.remove(0);
            if(!Compiler.Operator.contains(token)){
                output += token;
                if(!stack.empty()){
                    output += stack.pop();
                }
            }else{
                stack.push(token);
            }

        }
        System.out.println(output);

        stack.clear();
        
        return output;
    }
    
    protected boolean generateCode(String string) {
        //Generar el código ASM y pasarlo a un archivo .txt
        String operands = "";
        String operators = "";
        for (int i = 0; i < string.length(); i++) {
            if( Compiler.Operator.contains(((Character)string.charAt(i)).toString()) ) {
                operands = string.substring(0, i).trim();
                operators = string.substring(i, string.length()).trim();
                break;
            }
            
        }
        
        int index = 1;
        System.out.println("Operands: " + operands + "\nOperators: " + operators);
        this.STOVar = "a0";
        while(!operators.equals("")) {
            String action = "";
            String s1 = getLastOperand(operands);
            operands = operands.substring(0, operands.length() - 2);
            
            String s2 = getLastOperand(operands);
            operands = operands.substring(0, operands.length() - 2);
            
            String op = getFirstOperator(operators);
            operators = operators.substring(1);
            
            String res = "x" + index;
            operands = concatOperands(operands, res);
            
            switch(op) {
                case "+":
                    action = "ADD";
                    break;
                    
                case "-":
                    action = "SUB";
                    break;
                    
                case "*":
                    action = "MUL";
                    break;
                    
                case "/":
                case "%":
                    action = "DIV";
                    break;
                    
                default:
                    throw new RuntimeException();
            }
            String output = action + "\t" + s1 + "\t" + s2 + "\t" + res;
            this.res = res;
            System.out.println(output);
            try {
                out.write(output.getBytes());
                out.write("\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
//            System.out.println(s1 + "\t" + s2 + "\t" + op + "\t" + res + "\t");
//            System.out.println(operands);
//            System.out.println(operators);
            index++;
        }
        
        if (!STOVar.equals("")) {
            System.out.println("STO\t" + this.res + "\t" + STOVar);
            try {
                out.write(("STO\t" + this.res + "\t" + STOVar).getBytes());
                out.write("\n\n".getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
    
    private String getLastOperand(String string) {
        String s = string.substring(string.length() - 2);
        return s;
    }
    
    private String getFirstOperator(String string) {
        String s = string.substring(0, 1);
        return s;
    }
    
    private String concatOperands(String string, String operand) {
        return string + operand;
    }
    
    public void close() {
        try {
            out.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
