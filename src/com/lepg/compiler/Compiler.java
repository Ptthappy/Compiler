package com.lepg.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author Ptthappy
 */


public class Compiler {
    private final LexicalAnalyzer lexical = new LexicalAnalyzer();
    private final SyntacticalAnalyzer syntactical = new SyntacticalAnalyzer();
    private final SemanticalAnalyzer semantical;

    /*  Nunca está de más
    private final int declaringAndAsigning = 0;
    private final int declaring = 1;
    private final int asigning = 2;
    private final int expression = 3;
    */
    
    protected static int statementType;
    protected static int par = 0;  //Cantidad de paréntesis
    
    protected static final ArrayList<String> Number = new ArrayList<>();       //Numbers
    protected static final ArrayList<String> Operator = new ArrayList<>();    //Operators
    protected static final ArrayList<String> PrivateWord = new ArrayList<>();    //Private language's words
    protected static final ArrayList<String> Letter = new ArrayList<>();      //Letters
    protected static final ArrayList<String> Symbol = new ArrayList<>();      //All the other symbols
    
    protected static HashMap<String, String> premises = new HashMap<>();
    protected static Stack<String> stack = new Stack<>();
    protected static HashMap<String, String> table = new HashMap<>();
    
    protected static ArrayList<Character> actVar = new ArrayList<>();
    protected static ArrayList<ArrayList<String>> variables = new ArrayList<>();
    
    public Compiler() throws FileNotFoundException, IOException {
        semantical = new SemanticalAnalyzer();
        
        for (Character i = 48; i < 58; i++) { Number.add(i.toString()); }
        
        Operator.add("+");
        Operator.add("-");
        Operator.add("*");
        Operator.add("**");
        Operator.add("%");
        Operator.add("/");
        Operator.add("=");
        
        PrivateWord.add("int");
        PrivateWord.add("const");
        PrivateWord.add("float");
        PrivateWord.add("double");
        PrivateWord.add("boolean");
        PrivateWord.add("char");
        PrivateWord.add("string");
        
        for (Character i = 65; i < 123; i++) { //90 - 97
            Letter.add(i.toString());
            if (i == 90) i = 96;
        }
        
        Symbol.add("_");
        Symbol.add("$");
        Symbol.add(".");
        Symbol.add("(");
        Symbol.add(")");
    }
    
    protected  boolean compile(String input) {
        this.par = 0;
        String in = "";
        
//        semantical.testCodeGenerator("a6a5a4+*a3a2a1-/*");
        /**
         * String 1: a6a5a4; String 2: +*a3a2a1-/
         * -> a6x1; *a3a2a1-/
         * -> x2; a3a2a1-/
         */
        if((in = lexical.analyze(input)).equals("")) {
            System.out.println("Thrown error in Lexical");
            return false;
        }
        
        if(syntactical.analyze(in).equals("")) {
            System.out.println("Thrown error in Syntactical");
            return false;
        }
        
        if(!semantical.analyze(in))
           return false;

        return true;
    }
    
    public void close() {
        semantical.close();
    }
}
