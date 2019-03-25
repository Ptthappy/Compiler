package com.lepg.test;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * @author Ptthappy
 */


public class Main {
    
    
    public static void main(String[] args) {
        ArrayList<String> Number = new ArrayList<>();       //Numbers
        ArrayList<String> Operator = new ArrayList<>();    //Operators
        ArrayList<String> PrivateWord = new ArrayList<>();    //Private language's words
        ArrayList<String> Letter = new ArrayList<>();      //Letters
        ArrayList<String> Symbol = new ArrayList<>();      //All the other symbols
        
        HashMap<String, String> culo = new HashMap<>();
        
        for (Character i = 48; i < 58; i++) {
            Number.add(i.toString());
        }
        
        Operator.add("+");
        Operator.add("-");
        Operator.add("*");
        Operator.add("**");
        Operator.add("%");
        Operator.add("/");
        Operator.add("=");
        
        PrivateWord.add("int");
        PrivateWord.add("float");
        PrivateWord.add("double");
        PrivateWord.add("boolean");
        PrivateWord.add("char");
        PrivateWord.add("string");
        
        Letter.add("_");
        for (Character i = 65; i < 123; i++) { //90 - 97
            Letter.add(i.toString());
            if (i == 90) i = 96;
        }
        
        Symbol.add("$");
        Symbol.add(".");
        Symbol.add("(");
        Symbol.add(")");
        
        for (String i : Letter) {
            System.out.println(i);
            
        }
        /*
        String in = "12(345678)9";
        String in2 = in.substring(in.indexOf('(') + 1, in.indexOf(')'));
        in = in.substring(0, in.indexOf('('));
        System.out.println(in);
        System.out.println(in2);
        
        String a = "culo=culo";
        System.out.println(a.substring(a.indexOf("=") + 1));
        */
        
        culo.put("a1", "berga");
        System.out.println(culo.get("a1"));
        
        System.out.println(culo.getClass().getSimpleName());
        
    }
    
}
