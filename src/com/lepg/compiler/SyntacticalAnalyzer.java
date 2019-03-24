package com.lepg.compiler;

import java.util.ArrayList;

/**
 * @author Ptthappy
 */


public class SyntacticalAnalyzer {
    public boolean isLexem = true;
    public int inLength;
    
    public String analyze(String input) {
        String[] in = input.trim().split(" ");
        inLength = in.length;
        ArrayList<String> var = new ArrayList<>();
        
        switch(Compiler.statementType) {
            case 0:
                var.add(Compiler.table.get(in[0]));
                var.add(Compiler.table.get(in[1]));
                
                input = input.substring(input.indexOf('='));
                in = input.trim().split(" ");
                Integer result = calculate(in);
                if (result == null)
                    return "";
                
                var.add(result.toString());
                Compiler.variables.add(var);
                break;
                
            case 1:
                break;
                
            case 2:
                break;
                
            case 3:
                break;
                
            default:
                throw new RuntimeException();
        }
        
        return "";
    }
    
    private Integer calculate(String in) {
        Integer actualResult = 0;
        int next = 0;
        boolean par = false;
        String lastOperator = "";
        if (Compiler.par > 0) {
            par = true;
            Compiler.par--;
            String in2 = in.substring(in.indexOf(Compiler.Symbol.get(4)) + 1, in.indexOf(Compiler.Symbol.get(3)));
            in = in.substring(0, in.indexOf(Compiler.Symbol.get(4)));
            next = calculate(in2);
        }
        
        String[] lexems = in.split(" ");
        
        int x = 0;
        while((x = check(lexems)) != -1) {
            compress(lexems, x);
        }
        
        
        
        for (int i = 0; i < lexems.length; i++) {
            if (isLexem) {
                lexems[i] = Compiler.table.get(lexems[i]);
                if (isVar(lexems[i])) {
                    Integer x = search(lexems[i]);
                    
                    if (x == null)
                        return null;
                    
                    if (!lastOperator.equals("")) {
                        actualResult = solve(actualResult, x, lastOperator);
                    } else
                        actualResult = x;
                } else {
                    
                }
            } else {
                lastOperator = lexems[i];
            }
        }
        
        return 0;
    }
    
    private int check(String[] in) {
        int index = 0;
        for (String x : in) {
            if (x.equals("**"))
                return index;
            index++;
        }
        
        index = 0;
        for (String x : in) {
            if (x.equals("*") || x.equals("/") || x.equals("%"))
                return index;
            index++;
        }
        
        return -1;
    }
    
    private Integer search(String varName) {
        return null;
    }
    
    private boolean isVar(String shit) {
        return false;
    }
    
    private String[] compress(String[] shit, int index) {
        return null;
    }
    
    private Integer solve(int value1, int value2, String op) {
        switch(op) {
            case "+":
                break;
                
            case "-":
                break;
                
            case "*":
                break;
                
            case "/":
                break;
                
            case "**":
                break;
                
            case "%":
                break;
                
            default:
                return null;
                
        }
        return null;
    }
    
    private boolean checkPrivateWord(String[] words) {
        for (String word : words) {
            if(Compiler.PrivateWord.contains(word))
                return true;
        }
        
        return false;
    }
    
}
