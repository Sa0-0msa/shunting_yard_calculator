
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your expression: ");
        String userInput = scanner.nextLine();
        // remove whitespaces:
        userInput=userInput.replaceAll("\\s", ""); 
        Calculator calculator = new Calculator(userInput);
        System.out.println(calculator.getResult());
    }
}