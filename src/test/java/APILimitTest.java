import org.junit.Assert;
import org.junit.Test;

public class APILimitTest {
    public static final long ONE_SECOND_IN_NS = 10000000000L;

    // 1. Token Bucket empty => all call throttled.
    // 2. If more token are there in bucket.
    // 3. If less token are there in bucket.

    //1. First time ==> create a new bucket (on-demand)
    //2. Second Time onwards re-use same bucket.

    // 1. Client exist ==> rate limit on existing bucket
    //2. Client does not exist.  ==> rate limit on default max bucket size  (more than max (false), less than max(true))


    @Test
    public void smoke_test_token_Bucket_Token_Rejected() {
        //Arrange
        final TokenBucket tokenBucket = new TokenBucket(10, 1/ ONE_SECOND_IN_NS);

        //Act
        boolean isAllowed = tokenBucket.alloweRequest(50);

        //Assert
        Assert.assertFalse(isAllowed);
    }

    @Test
    public void smoke_test_token_Bucket_Token_Allowed() {
        //Arrange
        final TokenBucket tokenBucket = new TokenBucket(10, 1/ ONE_SECOND_IN_NS);

        //Act
        boolean isAllowed = tokenBucket.alloweRequest(5);


        //Assert
        Assert.assertTrue(isAllowed);
    }

    @Test
    public void smoke_test_token_Bucket_Token_Allowed_Multiple() throws InterruptedException {
        //Arrange
        final TokenBucket tokenBucket = new TokenBucket(10, 1/ ONE_SECOND_IN_NS);

        //Act
        boolean isAllowed = tokenBucket.alloweRequest(5);
        Thread.sleep(1000); // bad-design

        // Update the lastRefillTime

        isAllowed = tokenBucket.alloweRequest(2);


        //Assert
        Assert.assertTrue(isAllowed);
    }


}
