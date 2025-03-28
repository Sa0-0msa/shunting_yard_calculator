

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


class Token {
    enum TokenType { NUMBER, OPERATOR, LEFT_PARENTHESIS, RIGHT_PARENTHESIS,UNARY_MINUS};
    
    private final TokenType type;
    private final String value;

    public Token(TokenType type,String value){
        this.type = type;
        this.value = value;
    }

    public TokenType getType() { return type; }
    public String getValue() { return value; }
}


class Tokenizer {
    

    private final String expression;
    public Tokenizer(String expression){
        this.expression=expression;
    }
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        for(int i=0;i<expression.length();i++){
            char ch = expression.charAt(i);

            if(isDigit(ch))
            {
                StringBuilder number = new StringBuilder();
                int j=i;
                while(j<expression.length() && isDigit(expression.charAt(j)))
                {
                    number.append(expression.charAt(j));
                    j++;
                }

                tokens.add(new Token(Token.TokenType.NUMBER, number.toString()));
                i=j-1; // update i considering i++
            }

            else if("+^*/%".indexOf(ch)!=-1){
                tokens.add(new Token(Token.TokenType.OPERATOR, String.valueOf(ch)));
            }
            else if(ch=='-'){

                if(i==0 || 
                   i>0 && (tokens.get(tokens.size()-1).getType()==Token.TokenType.LEFT_PARENTHESIS || 
                   tokens.get(tokens.size()-1).getType()==Token.TokenType.OPERATOR)){
                        tokens.add(new Token(Token.TokenType.UNARY_MINUS,String.valueOf("~"))); 
                   } else{
                        tokens.add(new Token(Token.TokenType.OPERATOR,String.valueOf(ch)));
                   }

            }
            else if(ch=='(')
            {
                tokens.add(new Token(Token.TokenType.LEFT_PARENTHESIS,"("));
            }

            else if(ch==')')
            {
                tokens.add(new Token(Token.TokenType.RIGHT_PARENTHESIS,")"));
            }
            else 
            {
                throw new IllegalArgumentException("Illegal character: "+ch);
            }
        }

        return tokens;

    }

    private  boolean isDigit(char ch){
        return Character.isDigit(ch) || ch=='.';
    }
}

public class ShuntingYard {

  
    public List<String> infixToPostfix(List<Token> tokens) {
        Stack<String> operands = new Stack<>();
        List<String> postFixList = new ArrayList<>();

        for(int i=0;i<tokens.size();i++)
        {
            Token tk = tokens.get(i);
            String value = tk.getValue();
            if(null!=tk.getType())
            switch (tk.getType()) {
                case NUMBER -> postFixList.add(value);
                case OPERATOR -> {
                    while(!operands.isEmpty() && getPrecedence(value) <= getPrecedence(operands.peek()))
                    {
                        postFixList.add(operands.pop());
                    }   
                    operands.push(value);
                }
                case LEFT_PARENTHESIS -> operands.push(value);
                case RIGHT_PARENTHESIS -> {
                    while(!operands.isEmpty() && !operands.peek().equals("("))
                    {
                        postFixList.add(operands.pop());
                    }   operands.pop();
                }
                case UNARY_MINUS -> {
                    operands.push(value);
                }
                default -> {
                }
            }
        }
        while(!operands.isEmpty())
        {
            postFixList.add(operands.pop()); 
        }

        return postFixList;

    }


    private int getPrecedence(String operator) {
        
        return switch(operator) 
        {

            case "+","-" -> 1;
            case "*","/","%" -> 2;
            case "^" -> 3;
            case "~" ->4;

            
            default -> 0;
        };
    }
}

class Evaluator {
    
    public double evaluate(List<String> postFixExpression) {
        Stack<Double> operanDoubles = new Stack<>();
        for(String token : postFixExpression) {
            if(isOperator(token)){
                double operand2 = operanDoubles.pop();
                double operand1 = operanDoubles.pop();
                double result = performOperation(token,operand1,operand2);
                operanDoubles.push(result);
            }
            else if(isUnary(token)){
                double oper1= operanDoubles.pop();
                double result = performOperation(token, oper1,0.0);
                operanDoubles.push(result);
            }
            else{
                operanDoubles.push(Double.valueOf(token));
            }
        }

        if(operanDoubles.size()!=1)
            throw new IllegalArgumentException("Incorrect expression");

        return operanDoubles.pop();
    }

    private boolean isOperator(String token) {
        return (("+-*/^%").contains(token));
    }
    private boolean isUnary(String token) {
        return token.equals("~");
    }

    private double performOperation(String operator,Double oper1,Double oper2){
        return switch(operator){
            case "~" -> 0-oper1;
            case "+" -> oper1+oper2;
            case "-" -> oper1-oper2;
            case "*" -> oper1*oper2;
            case "/" -> oper1/oper2;
            case "%" -> oper1%oper2;
            case "^" -> Math.pow(oper1, oper2);
            default -> throw new IllegalArgumentException("Unknown operator: "+operator);
        };

    }

}