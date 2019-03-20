package com.lepg.compiler;


/**
 * @author Ptthappy
 */


public class SyntacticalAnalyzer {
    private boolean equal;
    private int type;
    
    /* int type
    * 0: Declaring and Asigning
    * 1: Declaring
    * 2: Asigning
    * 3: Expression
    */
    
    public boolean analyze(String input) {
        type = checkStatementType(input);
        System.out.println(type);
        
        if(type == -1) {
            return false;
        }
            
        
        else {
            
        }
        return true;
    }
    
    private int checkStatementType(String input) {
        int x = input.indexOf('=');
        
        if(x == -1) { //
            String a = input.substring(0, input.length() - 1);
            a = a.trim();
            String s[] = a.split(" ");
            if(checkPrivateWord(s)) {
                if(s.length == 2)
                    return 1;
                
                return -1;
            }
            return 3;
        }
        
        else {
            String s1 = input.substring(0, x);
            String s[] = s1.split(" ");
            
            switch(s.length) {
                case 1:
                    return 2;
                    
                case 2:
                    return 0;
                    
                default:  //Se supone que el lexical revisa esto anyway
                    return -1;
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
    
}
