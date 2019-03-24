package com.lepg.compiler;

import java.util.ArrayList;

/**
 * @author Ptthappy
 */


public class LexicalAnalyzer {
    private String cache = "";
    //private boolean declaring = false;
    
    /*  Nunca está de más
    private final int declaringAndAsigning = 0;
    private final int declaring = 1;
    private final int asigning = 2;
    private final int statement = 3;
    */
    
    private ArrayList<String> queue = new ArrayList<>();
    
    public String analyze(String input) {
        int type = checkStatementType(input);
        
        if (type == -1)
            return "";
        
        //switch aqui evaluando la verga dependiendo del tipo
        
        return "CULO VERDE";
    }
    
    private int checkStatementType(String input) {
        int x = input.indexOf('=');
        
        if(x == -1) { 
            String a = input.substring(0, input.length() - 1);
            a = a.trim();
            String s[] = a.split(" ");
            if(checkPrivateWord(s)) {
                if(s.length == 2)
                    return 1; //Declaring
                
                return -1;  //im chabista
            }
            return 3;  //Statement
        }
        /*
        im fuckign retard  -1
        let a = algo$       0
        let a$              1
        a = algo$           2
        algo$               3
        */
        
        else {
            String s1 = input.substring(0, x);
            String s[] = s1.split(" ");
            
            switch(s.length) {
                case 1: 
                    return 2;  //Asigning
                    
                case 2:
                    return 0;  //Declaring and Asigning
                    
                default:
                    return -1;  //im stupid
            }
        }
    }
    
    private boolean checkPrivateWord(String[] words) {
        for (String word : words) {
            if(Compiler.PrivateWord.contains(word))
                return true;
        }
        
        return false;
    }
    
    //Este es el viejo mardicion me quiero morir
    /*public boolean analyze(String input) {
        declaring = false;
        cache = "";
        queue.clear();
        try {
            if(Compiler.PrivateWord.contains(input.substring(0, input.indexOf(" ")))) {
                declaring = true;
                int space = input.indexOf(" ");
                queue.add(input.substring(0, space));
                input = input.substring(space + 1);
            } else
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
                if (Compiler.Letter.contains(x) || (Compiler.Number.contains(x) && !cache.equals(""))) {
                    cache += x;
                    if(Compiler.PrivateWord.contains(cache))
                        return false;
                }
                    
                else
                    return false;
            }
            cache = x;
        }
        
        int par = 0;
        boolean privateFound = false;
        while(!queue.isEmpty()) {
            String x = queue.remove(0);
            
            if(cache.equals("")) {
                if(Compiler.Number.contains(x) || Compiler.Letter.contains(x)) {
                    cache = x;
                    continue;
                }
                else
                    return false;
            }
            
            if(privateFound) {
                if(Compiler.Letter.contains(x))
                    privateFound = false;
                else
                    return false;
            }
            
            if(Compiler.Number.contains(x)) {
                if (Compiler.Number.contains(cache) || Compiler.Operator.contains(cache) || 
                        Compiler.Letter.contains(cache) || cache.equals(Compiler.Symbol.get(1)) 
                        || cache.equals(Compiler.Symbol.get(2))) {
                    cache = x;
                    continue;
                }
                else {
                    return false;
                }
                    
            }
            
            if(Compiler.Operator.contains(x)) {
                if(x.equals(Compiler.Operator.get(6)))
                    return false;
                
                if(Compiler.Number.contains(cache) || Compiler.Letter.contains(cache) || cache.equals(Compiler.Symbol.get(3))) {
                    cache = x;
                    continue;
                }
                else
                    return false;
            }
            
            if(Compiler.Letter.contains(x)) {
                String cachecito = cache.charAt(cache.length() - 1) + "";
                if(Compiler.Letter.contains(cachecito)) {
                    cache += x;
                    if(Compiler.PrivateWord.contains(cache)) {
                        privateFound = true;
                    }
                    continue;
                }
                
                else if(Compiler.Operator.contains(cachecito) || cachecito.equals(Compiler.Symbol.get(2))) {
                    cache = x;
                    continue;
                }
                
                else
                    return false;
            }
            
            if(Compiler.Symbol.contains(x)) {
                if(x.equals(Compiler.Symbol.get(0))) {
                    cache = cache.charAt(cache.length() - 1) + "";
                    if((Compiler.Number.contains(cache) || Compiler.Letter.contains(cache) || 
                            cache.equals(Compiler.Symbol.get(3))) && (queue.isEmpty()
                            && par == 0))
                        return true;
                    else
                        return false;
                }
                
                if(x.equals(Compiler.Symbol.get(1))) {
                    if(Compiler.Number.contains(cache)) {
                        cache = x;
                        continue;
                    }
                    else
                        return false;
                }
                
                if(x.equals(Compiler.Symbol.get(2))) {
                    if(Compiler.Operator.contains(cache) || cache.equals(Compiler.Symbol.get(2))) {
                        par++;
                        cache = x;
                        continue;
                    }
                    else
                        return false;
                }
                
                if(x.equals(Compiler.Symbol.get(3))) {
                    if(Compiler.Number.contains(cache) || Compiler.Letter.contains(cache) || cache.equals(Compiler.Symbol.get(3))) {
                        par--;
                        cache = x;
                        continue;
                    }
                    else
                        return false;
                }
            }
            return false;
        }
        return false;
        
    }*/
        
}
