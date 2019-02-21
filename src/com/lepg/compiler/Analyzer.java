package com.lepg.compiler;

import java.util.ArrayList;

/**
 * @author Ptthappy
 */


public class Analyzer {
    private final ArrayList<String> Number = new ArrayList<>();       //Numbers
    private final ArrayList<String> Operator = new ArrayList<>();    //Operators
    private final ArrayList<String> PrivateWord = new ArrayList<>();    //Private language's words
    private final ArrayList<String> Letter = new ArrayList<>();      //Letters
    private final ArrayList<String> Symbol = new ArrayList<>();      //All the other symbols
    
    private String cache = "";
    private boolean declaring;
    
    private ArrayList<String> queue = new ArrayList<>();
    
    public Analyzer() {
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
    }
    
    public boolean analyze(String input) {
        queue.clear();
        try {
            if(PrivateWord.contains(input.substring(0, input.indexOf(" ")))) {
                declaring = true;
                int space = input.indexOf(" ");
                queue.add(input.substring(0, space));
                input = input.substring(space + 1);
            }
            else
                declaring = false;
        } catch (StringIndexOutOfBoundsException e) {}
        
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') continue;
            queue.add(input.charAt(i) + "");
        }
        
        if(declaring) {
            String x;
            cache = "";
            queue.remove(0);
            while(!(x = queue.remove(0)).equals("=")) {
                if (Letter.contains(x) || (Number.contains(x) && !cache.equals("")))
                    cache += x;
            else
                return false;
            }
            if(PrivateWord.contains(cache))
                return false;
            cache = x;
        }
        
        int par = 0;
        boolean privateFound = false;
        while(!queue.isEmpty()) {
            String x = queue.remove(0);
            
            if(cache.equals("")) {
                if(Number.contains(x) || Letter.contains(x)) {
                    cache = x;
                    continue;
                }
                else
                    return false;
            }
            
            if(privateFound) {
                if(Letter.contains(x))
                    privateFound = false;
                else
                    return false;
            }
            
            if(Number.contains(x)) {
                if (Number.contains(cache) || Operator.contains(cache) || Letter.contains(cache) || cache.equals(Symbol.get(1)) 
                        || cache.equals(Symbol.get(2))) {
                    cache = x;
                    continue;
                }
                else {
                    return false;
                }
                    
            }
            
            if(Operator.contains(x)) {
                if(x.equals(Operator.get(6)))
                    return false;
                
                if(Number.contains(cache) || Letter.contains(cache) || cache.equals(Symbol.get(3))) {
                    cache = x;
                    continue;
                }
                else
                    return false;
            }
            
            if(Letter.contains(x)) {
                String cachecito = cache.charAt(cache.length() - 1) + "";
                if(Letter.contains(cachecito)) {
                    cache += x;
                    if(PrivateWord.contains(cache)) {
                        privateFound = true;
                    }
                    continue;
                }
                
                else if(Operator.contains(cachecito) || cachecito.equals(Symbol.get(2))) {
                    cache = x;
                    continue;
                }
                
                else
                    return false;
            }
            
            if(Symbol.contains(x)) {
                if(x.equals(Symbol.get(0))) {
                    cache = cache.charAt(cache.length() - 1) + "";
                    if((Number.contains(cache) || Letter.contains(cache) || cache.equals(Symbol.get(3))) && queue.isEmpty())
                        return true;
                    else
                        return false;
                }
                
                if(x.equals(Symbol.get(1))) {
                    if(Number.contains(cache)) {
                        cache = x;
                        continue;
                    }
                    else
                        return false;
                }
                
                if(x.equals(Symbol.get(2))) {
                    if(Operator.contains(cache)) {
                        par++;
                        cache = x;
                        continue;
                    }
                    else
                        return false;
                }
                
                if(x.equals(Symbol.get(3))) {
                    if(Number.contains(cache) || Letter.contains(cache)) {
                        par--;
                        cache = x;
                        continue;
                    }
                    else
                        return false;
                }
            }
            System.out.println("XD1");
            return false;
        }
        System.out.println("XD2");
        return false;
        
    }
    
}
