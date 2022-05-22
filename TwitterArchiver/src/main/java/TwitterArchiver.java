import twitter4j.*;
import java.util.List;
import java.util.Scanner;


public class TwitterArchiver {
    public static void main(String args[]) throws TwitterException {
        //Create a Scanner object called userInput to read user input from keyboard
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter the keyword that you want to search with: ");

        //Read userInput string and store in variable keyword
        String keyword = userInput.nextLine();
        System.out.println(keyword);

        //Close Scanner object
        userInput.close();

        //Create TwitterFactory() object called twitter
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Query queryKeyword = new Query(keyword);   //query user-input keyword
            queryKeyword.setCount(2);  //limit search to 10 tweets
            QueryResult result = twitter.search(queryKeyword);   //search for tweets

            System.out.println("Retrieving tweets, please wait!");  //Output message
            List<Status> tweets = result.getTweets();   //Store tweets in an ArrayList
            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser() + "-----" + tweet.getText());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
