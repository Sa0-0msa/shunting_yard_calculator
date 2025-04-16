
import java.util.Scanner;



public class Main {


    public static void start(){
        String GREEN = "\u001B[32m";
         String RESET = "\u001B[0m";
        int size = 29;
        char[] length = new char[size];
        for(int i=0;i<size;i++) {
            length[i]='*';
        }
        String message = "Shunting_Yard_Calculator!";
        System.out.print(GREEN);
        for(char ch:length)
            System.out.print(ch);
        System.out.println();
        System.out.println("* "+message+" *");
         for(char ch:length)
            System.out.print(ch);
        System.out.println();
        System.out.print(RESET);
    }

    public static void main(String[] args) {
        start();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your expression: ");
        String userInput = scanner.nextLine();
        // remove whitespaces:
        userInput=userInput.replaceAll("\\s", ""); 

        Calculator calculator = new Calculator(userInput);
        System.out.println(calculator.getResult());


    }
}