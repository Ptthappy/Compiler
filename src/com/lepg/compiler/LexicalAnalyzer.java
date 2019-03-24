package com.lepg.compiler;

import java.util.*;
import java.util.stream.IntStream;

/**
 * @author Ptthappy
 */


public class LexicalAnalyzer {
    private String cache = "";
    //private boolean declaring = false;
    

    private final int declaringAndAsigning = 0;
    private final int declaring = 1;
    private final int asigning = 2;
    private final int statement = 3;

    
    private ArrayList<String> queue = new ArrayList<>();
    
    public String analyze(String input) {
        int openingCount = 0;
        int closingCount = 0;
        int equalCount = 0;
        int endCharacter = 0;
        for(int i = 0; i < input.length(); i++){
            if(input.charAt(i) == '('){
                openingCount++;
            }else if(input.charAt(i) == ')'){
                closingCount++;
            }else if(input.charAt(i) == '$'){
                endCharacter++;
            }else if(input.charAt(i) == '='){
                equalCount++;
            }
        }

        if(openingCount != closingCount || endCharacter > 1 || equalCount > 1) return "";

        if(input.trim().indexOf("$") != input.trim().length() - 1) return "";

        ArrayList<String> tokens = new ArrayList<String>();
        Collections.addAll(tokens, input.trim().split(" "));


        for(int i = 0; i < tokens.size(); i++){
            System.out.print(tokens.get(i) + " ");
        }

        System.out.println();

        ListIterator<String> iter = tokens.listIterator();
        ArrayList<String> newTokens = new ArrayList<>();
        while(iter.hasNext()){
            String s = iter.next();
            if(s.length() > 1 && s.charAt(0) == '('){
                int count = 0;
                for(int i = 0; i < s.length(); i++){
                    if(s.charAt(i) == '('){
                        count++;
                    }else{
                        break;
                    }
                }

                int parenthCount = count;
                while(parenthCount > 0){
                    newTokens.add("(");
                    parenthCount--;
                }
                newTokens.add(s.substring(count));
            }else if(s.length() > 1 && s.charAt(s.length() - 1) == ')'){
                int count = 0;
                for(int i = s.length() - 1; i > 0; i--){
                    if(s.charAt(i) == ')'){
                        count++;
                    }else{
                        break;
                    }
                }

                int parenthCount = count;
                newTokens.add(s.substring(0, s.length()-(count)));
                while(parenthCount > 0){
                    newTokens.add(")");
                    parenthCount--;
                }
            }else if(s.length() > 1 && s.charAt(s.length() - 1) == '$'){
                newTokens.add(s.substring(0, s.length() - 1));
                newTokens.add("$");
            }else{
                newTokens.add(s);
            }
        }
        for(int i = 0; i < newTokens.size(); i++){
            System.out.print(newTokens.get(i) + " ");
        }

        System.out.println();

        if(newTokens.contains("=")){
            int equalIndex = newTokens.indexOf("=");
            if(equalIndex == 0 || equalIndex > 2){
                return "";
            }else{
                if(equalIndex == 1){
                    System.out.println("Assigning");
                    Compiler.statementType = 2; //assigning

                    String variableName = newTokens.get(0);
                    if(Compiler.Number.contains(variableName.charAt(0) + "") || Compiler.PrivateWord.contains(variableName)){
                        return "";
                    }
                    for(String str: variableName.split("")){
                        if(Compiler.Operator.contains(str) || Compiler.Symbol.contains(str)){
                            if(!str.equals("_")) return "";
                        }else if(!Compiler.Letter.contains(str) && !Compiler.Number.contains(str)){
                            return "";
                        }
                    }
                    String next = "Var";



                }else if(equalIndex == 2){
                    System.out.println("Declaring and Assigning");
                    Compiler.statementType = 0; //declaring and assigning

                    String variableName = newTokens.get(1);
                    if(Compiler.Number.contains(variableName.charAt(0) + "") || Compiler.PrivateWord.contains(variableName)){
                        return "";
                    }
                    for(String str: variableName.split("")){
                        if(Compiler.Operator.contains(str) || Compiler.Symbol.contains(str)){
                            if(!str.equals("_")) return "";
                        }else if(!Compiler.Letter.contains(str) && !Compiler.Number.contains(str)){
                            return "";
                        }
                    }

                    String reservedWord = newTokens.get(0);
                    if(!Compiler.PrivateWord.contains(reservedWord)){
                        return "";
                    }



                }
            }

        }else{
            if(newTokens.size() == 3){
                System.out.println("Declaring");
                Compiler.statementType = 1; //declaring

                String reservedWord = newTokens.get(0);
                if(!Compiler.PrivateWord.contains(reservedWord)){
                    return "";
                }

                String variableName = newTokens.get(1);
                if(Compiler.Number.contains(variableName.charAt(0) + "") || Compiler.PrivateWord.contains(variableName)){
                    return "";
                }
                for(String str: variableName.split("")){
                    if(Compiler.Operator.contains(str) || Compiler.Symbol.contains(str)){
                        if(!str.equals("_")) return "";
                    }else if(!Compiler.Letter.contains(str) && !Compiler.Number.contains(str)){
                        return "";
                    }
                }

            }else if(newTokens.size() > 3 || newTokens.size() <= 2) {
                System.out.println("Expression");
                Compiler.statementType = 3; //expression
                if(Compiler.PrivateWord.contains(newTokens.get(0))){
                    return "";
                }

                String next = "Value";
                if(newTokens.size() > 3){
                    for (int i = 0; i < newTokens.size(); i++){
                        String token = newTokens.get(i);
                        if(token.equals("(") || token.equals(")")) continue;
                        if(token.length() == 1){
                            if(Compiler.Operator.contains(token)){
                                if(!next.equals("Operator")){
                                    return "";
                                }
                                next = "Value";
                            }else if(Compiler.Number.contains(token) || Compiler.Symbol.contains(token) || Compiler.Letter.contains(token)){
                                next = "Operator";
                                continue;
                            }
                        }else{
                            if(token.equals("**")){
                               if(!next.equals("Operator")){
                                   return "";
                               }
                               next = "Value";
                            }else{
                                if(!next.equals("Value")){
                                    return "";
                                }
                                next = "Operator";
                            }
                        }
                    }
                }

            }
        }

        int count = 1;

        ArrayList<String> output = new ArrayList<>();
        for(String token : newTokens){
            if(!Compiler.Symbol.contains(token) && !Compiler.Operator.contains(token)){
                output.add("a" + count);
                Compiler.table.put("a" + count, token);
                count++;
            }else{
                output.add(token);
            }
        }

        System.out.println("TABLE:");
        Compiler.table.forEach((a, b) ->{
            System.out.println("[ " + a + " ] | [ " + b + " ]");
        });

        System.out.println("OUTPUT:");
        for(int i = 0; i < output.size(); i++){
            System.out.print(output.get(i) + " ");
        }
        System.out.println();

        Compiler.table.clear();

        return "lexical complete";
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
