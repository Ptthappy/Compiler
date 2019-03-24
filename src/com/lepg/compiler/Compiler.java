package com.lepg.compiler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * @author Ptthappy
 */


public class Compiler {
    private final LexicalAnalyzer lexical = new LexicalAnalyzer();
    private final SyntacticalAnalyzer syntactical = new SyntacticalAnalyzer();
    private final SemanticalAnalyzer semantical = new SemanticalAnalyzer();

    /*  Nunca está de más
    private final int declaringAndAsigning = 0;
    private final int declaring = 1;
    private final int asigning = 2;
    private final int expression = 3;
    */
    public static int statementType;
    public static int par = 0;  //Cantidad de paréntesis
    
    public static final ArrayList<String> Number = new ArrayList<>();       //Numbers
    public static final ArrayList<String> Operator = new ArrayList<>();    //Operators
    public static final ArrayList<String> PrivateWord = new ArrayList<>();    //Private language's words
    public static final ArrayList<String> Letter = new ArrayList<>();      //Letters
    public static final ArrayList<String> Symbol = new ArrayList<>();      //All the other symbols
    
    public static HashMap<String, String> premises = new HashMap<>();
    public static Stack<String> stack = new Stack<>();
    public static HashMap<String, String> table = new HashMap<>();
    
    public static ArrayList<Character> actVar = new ArrayList<>();
    public static ArrayList<ArrayList<String>> variables = new ArrayList<>();
    
    public Compiler() {
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
        PrivateWord.add("var");
        PrivateWord.add("let");
        
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
    
    public boolean compile(String input) {
        if(lexical.analyze(input).equals(""))
            return false;
        
        if(syntactical.analyze(input).equals(""))
            return false;
        
//      if(!semantical.analyze(input))
//          return false;

        return true;
    }
}
