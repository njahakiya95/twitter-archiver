import twitter4j.*;

public class TwitterArchiver {
    public static void main(String args[]) {
        try {
            String latestStatus = "Tweet sent";
            Twitter twitter = TwitterFactory.getSingleton();
            Status status = twitter.updateStatus(latestStatus);
            System.out.println("Successfully updated the status to [" + status.getText() + "].");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
