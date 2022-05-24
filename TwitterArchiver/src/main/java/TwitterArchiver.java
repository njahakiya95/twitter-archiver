import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import twitter4j.*;
import java.util.List;
import java.util.Scanner;

import com.mongodb.util.JSON;


public class TwitterArchiver {
    //tweets will help store tweets into <keyword> collection
    private static DBCollection tweets;
    public static void main(String args[]) throws TwitterException {
        //Create a Scanner object called userInput to read user input from keyboard
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter the keyword that you want to search with: ");

        //Read userInput string and store in variable keyword
        String keyword = userInput.nextLine();
        System.out.println(keyword);

        //Close Scanner object
        userInput.close();

        //Connect with MongoDB client
        MongoClient client = new MongoClient(new ServerAddress("localhost", 27017));

        //Get tweetDB which holds the tweets collection and tweet documents
        MongoDatabase database = client.getDatabase("tweetDB");

        //Create a collection based on the keyword entered
        tweets = database.createCollection(keyword);

        //Create TwitterFactory() object called twitter
        try {
            Twitter twitter = new TwitterFactory().getInstance();
            Query queryKeyword = new Query(keyword + " exclude:retweets");   //query user-input keyword
            queryKeyword.setCount(100);  //limit search to 10 tweets
            QueryResult result = twitter.search(queryKeyword);   //search for tweets

            System.out.println("Retrieving tweets, please wait!");  //Output message
            List<Status> tweets = result.getTweets();   //Store tweets in an ArrayList
            for (Status tweet : tweets) {
                System.out.println("@" + tweet.getUser() + "  " + tweet.getText());
                System.out.println("\n");
                System.out.println("===========================================================================");
                System.out.println("\n");

                //Store tweet information in tweetStore object
                BasicDBObject tweetStore = new BasicDBObject();
                tweetStore.put("user_name",tweet.getUser().getScreenName());
                tweetStore.put("retweet_count",tweet.getRetweetCount());
                tweetStore.put("source",tweet.getSource());
                tweetStore.put("tweet_id",tweet.getId());
                tweetStore.put("tweet_text",tweet.getText());

                //Save tweetStore object to <keyword> collection
                tweets.insert(tweetStore); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
