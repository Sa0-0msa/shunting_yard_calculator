

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Represents a token in a mathematical expression for parsing and evaluation.
 * Each token has a type and a string value.
 */
class Token {
    /**
     * Enum defining the possible types of tokens in a mathematical expression.
     * NUMBER: Numeric values
     * OPERATOR: Binary operators like +, -, *, /, etc.
     * LEFT_PARENTHESIS: Opening parenthesis (
     * RIGHT_PARENTHESIS: Closing parenthesis )
     * UNARY_MINUS: Negative sign as a unary operator
     * FUNCTION: Mathematical functions like sin, cos
     */
    enum TokenType { NUMBER, OPERATOR, LEFT_PARENTHESIS, RIGHT_PARENTHESIS,UNARY_MINUS,FUNCTION};
    
    /**
     * The type of this token, as defined in the TokenType enum
     */
    private final TokenType type;

    /**
     * The string value of this token (e.g., "5", "+", "sin")
     */
    private final String value;

    /**
     * Constructs a new Token with the specified type and value.
     *
     * @param type The TokenType of this token
     * @param value The string value of this token
     */
    public Token(TokenType type,String value){
        this.type = type;
        this.value = value;
    }
     /**
     * Returns the type of this token.
     *
     * @return The TokenType of this token
     */
    public TokenType getType() { return type; }

    /**
     * Returns the string value of this token.
     *
     * @return The string value of this token
     */
    public String getValue() { return value; }
}



/**
 * Tokenizes a mathematical expression string into a list of Token objects.
 * This class parses expressions containing numbers, basic operators,
 * parentheses, and trigonometric functions (sin, cos).
 */
class Tokenizer {
    
    /**
     * Regular expression pattern to match valid tokens in the mathematical expression.
     * Matches:
     * - Trigonometric functions (sin, cos)
     * - Numbers (integers or decimals)
     * - Operators (+, -, *, /, ^)
     * - Parentheses
     */
    private final String pattern = "sin|cos|"+
                                    "\\d+\\.?\\d*|"+
                                    "\\+|\\*|-|/|\\^|"+
                                    "\\(|\\)";

    /**
     * The mathematical expression to tokenize
     */
    private final String expression;

    /**
     * Constructs a new Tokenizer for the specified expression.
     *
     * @param expression The mathematical expression to tokenize
     */
    public Tokenizer(String expression){
        this.expression=expression;
    }

    /**
     * Tokenizes the expression into a list of Token objects.
     * Special handling is applied for the minus sign to distinguish between
     * binary subtraction and unary negation.
     *
     * @return List of Token objects representing the expression
     */
    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expression);
         // Find all tokens in the expression using regex matching
        while(m.find()){
            String token = m.group();
            // Classify each token based on its value
            if (isNumber(token)){
                tokens.add(new Token(Token.TokenType.NUMBER,token));
            }
            else if(token.equals("sin") || token.equals("cos")){
                tokens.add(new Token(Token.TokenType.FUNCTION,token));
            }
            else if("+^*/".contains(token)){
                tokens.add(new Token(Token.TokenType.OPERATOR,token));
            }
            else if(token.equals("-")){
                // Determine if this is a unary minus or binary subtraction operator
                if(tokens.isEmpty()||tokens.getLast().getType()==Token.TokenType.LEFT_PARENTHESIS ||
                tokens.getLast().getType()==Token.TokenType.OPERATOR){
                    tokens.add(new Token(Token.TokenType.UNARY_MINUS,"~"));
                }
                else{
                    tokens.add(new Token(Token.TokenType.OPERATOR,"-"));
                }
            }
            else if(token.equals("(")){
                tokens.add(new Token(Token.TokenType.LEFT_PARENTHESIS,"("));
            }
             else if(token.equals(")"))
            {
                tokens.add(new Token(Token.TokenType.RIGHT_PARENTHESIS,")"));
            }
        }
        return tokens;
    }

    /**
     * Checks if a string represents a number (integer or decimal).
     *
     * @param str The string to check
     * @return true if the string is a number, false otherwise
     */
     private boolean isNumber(String str){
            return str.matches("\\d+\\.?\\d*");
        }
}

public class ShuntingYard {

    /**
     * Converts an infix expression to postfix notation using the Shunting Yard algorithm.
     * This implementation handles basic operators, parentheses, unary minus, and functions (sin, cos).
     *
     * @param tokens List of tokens representing the infix expression
     * @return List of strings representing the expression in postfix notation
     */

  
    public List<String> infixToPostfix(List<Token> tokens) {
        Stack<String> operands = new Stack<>(); // Stack to hold operators temporarily
        List<String> postFixList = new ArrayList<>(); // Output list for postfix expression

        // Process each token in the input list
        for(int i=0;i<tokens.size();i++)
        {
            Token tk = tokens.get(i);
            String value = tk.getValue();
            // Process token based on its type
            if(null!=tk.getType())
            switch (tk.getType()) {
                case NUMBER -> postFixList.add(value); // Numbers go directly to output
                case OPERATOR -> {
                     // Pop operators with higher or equal precedence from the stack
                    while(!operands.isEmpty() && getPrecedence(value) <= getPrecedence(operands.peek()))
                    {
                        postFixList.add(operands.pop());
                    }   
                    operands.push(value); // Push current operator to stack
                }
                case LEFT_PARENTHESIS -> operands.push(value);
                case RIGHT_PARENTHESIS -> {
                    // Pop operators until matching left parenthesis is found
                    while(!operands.isEmpty() && !operands.peek().equals("("))
                    {
                        postFixList.add(operands.pop());
                    }   
                    operands.pop(); // Remove the left parenthesis
                }
                case UNARY_MINUS -> {
                    operands.push(value); // Push unary minus to stack
                }
                case FUNCTION -> {
                    operands.push(value); // Push function name to stack
                }
                default -> {
                    // Ignore other token types
                }
            }
        }
        // Pop any remaining operators to output
        while(!operands.isEmpty())
        {
            postFixList.add(operands.pop()); 
        }
        return postFixList;
    }


    /**
     * Determines the precedence level of an operator.
     * Higher values indicate higher precedence.
     *
     * @param operator The operator string
     * @return Integer value representing the operator's precedence
     */

    private int getPrecedence(String operator) {
         // Return precedence based on operator type
        return switch(operator) 
        {

            case "+","-" -> 1;  // Addition, subtraction (lowest)
            case "*","/","%" -> 2; // Multiplication, division, modulo
            case "^" -> 3; // Exponentiation
            case "~" ->4; // Unary minus (higher precedence)
            case "sin","cos" -> 5; // Trigonometric functions (highest)

            
            default -> 0; // Default for parentheses and unknown operators
        }; 
    }
}


/**
 * Evaluates a mathematical expression in postfix notation.
 * Supports basic arithmetic operators, unary minus, and trigonometric functions.
 */
class Evaluator {
    
    /**
     * Evaluates a postfix expression and returns the result.
     * Uses a stack-based algorithm to process operands and operators.
     *
     * @param postFixExpression List of tokens in postfix notation
     * @return The result of evaluating the expression
     * @throws IllegalArgumentException if the expression is invalid
     */
    public double evaluate(List<String> postFixExpression) {
        Stack<Double> operanDoubles = new Stack<>();

        // Process each token in the postfix expression
        for(String token : postFixExpression) {
            if(isOperator(token)){
                // Binary operators require two operands
                double operand2 = operanDoubles.pop();
                double operand1 = operanDoubles.pop();
                double result = performOperation(token,operand1,operand2);
                operanDoubles.push(result);
            }
            else if(isUnary(token)){
                // Unary operators require one operand
                double oper1= operanDoubles.pop();
                double result = performOperation(token, oper1,0.0);
                operanDoubles.push(result);
            }
            else if(isFunction(token)){
                 // Functions require one operand
                double oper1 = operanDoubles.pop();
                double result;
                if(token.equals("sin")){
                    result = performFunction(token, oper1);
                    operanDoubles.push(result);
                }
                else {
                    result = performFunction(token, oper1);
                    operanDoubles.push(result);
                }
            }
            else{
                // Numbers are pushed directly onto the stack
                operanDoubles.push(Double.valueOf(token));
            }
        }
        // At the end, there should be exactly one value on the stack
        if(operanDoubles.size()!=1)
            throw new IllegalArgumentException("Incorrect expression");

        return operanDoubles.pop();
    }

    /**
     * Checks if a token is a binary operator.
     *
     * @param token The token to check
     * @return true if the token is a binary operator, false otherwise
     */
    private boolean isOperator(String token) {
        return (("+-*/^%").contains(token));
    }

    /**
     * Checks if a token is a unary operator.
     *
     * @param token The token to check
     * @return true if the token is a unary operator, false otherwise
     */
    private boolean isUnary(String token) {
        return token.equals("~");
    }

     /**
     * Checks if a token is a mathematical function.
     *
     * @param token The token to check
     * @return true if the token is a function, false otherwise
     */
    private boolean isFunction(String token){
        return token.equals("sin") || token.equals("cos");
    }


     /**
     * Performs the specified binary or unary operation on the operands.
     *
     * @param operator The operator to apply
     * @param oper1 The first operand
     * @param oper2 The second operand (used only for binary operators)
     * @return The result of the operation
     * @throws IllegalArgumentException if the operator is unknown
     */
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

    /**
     * Performs the specified mathematical function on the operand.
     *
     * @param function The function to apply
     * @param oper1 The operand
     * @return The result of the function
     * @throws IllegalArgumentException if the function is unknown
     */
    private double performFunction(String function,Double oper1){
        return switch(function){
            case "sin" -> Math.sin(oper1);
            case "cos" -> Math.cos(oper1);
            default -> throw new IllegalArgumentException("Unknown function: "+function);
        };
    }

}