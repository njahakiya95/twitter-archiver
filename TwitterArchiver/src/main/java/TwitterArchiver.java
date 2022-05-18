import twitter4j.*;
import java.util.Scanner;


public class TwitterArchiver {
    public static void main(String args[]) {

        try {
            //Create a Scanner object called userInput to read user input from keyboard
            Scanner userInput = new Scanner(System.in);
            System.out.println("Enter the keyword that you want to search with: ");

            //Read userInput string and store in variable keyword
            String keyword = userInput.nextLine();
            System.out.println(keyword);

            //Close Scanner object
            userInput.close();
        }
        catch (Exception e){
            //Output error message in case user input is invalid
            System.out.println("Invalid/Illegal user keyword input");

        }
        
    }
}
