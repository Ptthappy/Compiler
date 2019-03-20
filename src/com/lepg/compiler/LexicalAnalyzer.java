package com.lepg.compiler;

import java.util.ArrayList;

/**
 * @author Ptthappy
 */


public class LexicalAnalyzer {
    private String cache = "";
    private boolean declaring = false;
    
    private ArrayList<String> queue = new ArrayList<>();
    
    public boolean analyze(String input) {
        declaring = false;
        cache = "";
        queue.clear();
        try {
            if(Compiler.PrivateWord.contains(input.substring(0, input.indexOf(" ")))) {
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
        
    }
        
}
