
import java.util.List;

public class Calculator {
    private final double result;
    private final String userInput;
    private final Tokenizer tokenizer;
    private final  ShuntingYard shuntingYard;
    private final Evaluator evaluator;
    public Calculator(String input){
        userInput=input;
        tokenizer = new Tokenizer(userInput);
        shuntingYard = new ShuntingYard();
        evaluator = new Evaluator();
        List<String> infixtoPostfix = shuntingYard.infixToPostfix(tokenizer.tokenize());
        result = evaluator.evaluate(infixtoPostfix);
    }


    public double getResult() { return result; }
}