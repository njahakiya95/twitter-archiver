import com.mongodb.client.*;
import com.mongodb.client.MongoClient;
import org.bson.Document;
import org.bson.conversions.Bson;
import twitter4j.*;
import java.util.List;
import java.util.Scanner;
import com.mongodb.*;

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

        //Create mongoClient to connect to database
        MongoClient mongoClient = MongoClients.create();

        //Get database tweetDB (will automatically be created if it doesn't exist)
        MongoDatabase database = mongoClient.getDatabase("tweetDB");

        //Create a collection based on the keyword to store the tweets
        MongoCollection<Document> tweetKeyword = database.getCollection(keyword);

        //Create TwitterFactory() object called twitter
        Twitter twitter = new TwitterFactory().getInstance();
        Query queryKeyword = new Query(keyword + " exclude:retweets");   //query user-input keyword and exclude any retweets
        queryKeyword.setCount(100);  //limit search to 10 tweets
        QueryResult result = twitter.search(queryKeyword);   //search for tweets based on keyword

        System.out.println("Retrieving tweets, please wait!\n");  //Output message
        List<Status> tweets = result.getTweets();   //Store tweets in an ArrayList

        for (Status tweet : tweets) {
            //Print each found tweet along with username
            System.out.println("@" + tweet.getUser() + "  " + tweet.getText());
            System.out.println("\n");
            System.out.println("===========================================================================");
            System.out.println("\n");

            //Use tweetObj to store the tweets in the <keyword> collection
            BasicDBObject tweetObj = new BasicDBObject();
            tweetObj.put("user_name",tweet.getUser().getScreenName());
            tweetObj.put("retweet_count",tweet.getRetweetCount());
            tweetObj.put("source",tweet.getSource());
            tweetObj.put("tweet_id",tweet.getId());
            tweetObj.put("tweet_text",tweet.getText());
        };
    };
}