
import java.util.Scanner;



public class Main {


    public static void draw(){
        String GREEN = "\u001B[32m";  // Green color
        String BOLD = "\u001B[1m";    // Bold text
        String RESET = "\u001B[0m";   // Reset formatting
        int size = 29;
        char[] length = new char[size];
        for(int i=0;i<size;i++) {
            length[i]='*';
        }
        System.out.println(GREEN+BOLD);
        System.out.println("╔═══════════════════════════╗");
        System.out.println("║ Shunting_Yard_Calculator! ║");
        System.out.println("╚═══════════════════════════╝");
        System.out.println(RESET);
    }

    public static void main(String[] args) {
        draw();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your expression: ");
        String userInput = scanner.nextLine();
        // remove whitespaces:
        userInput=userInput.replaceAll("\\s", ""); 

        Calculator calculator = new Calculator(userInput);
        System.out.println(calculator.getResult());


    }
}