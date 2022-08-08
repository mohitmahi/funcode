import com.google.common.annotations.VisibleForTesting;
import lombok.Setter;

public class TokenBucket {
    private final long maxBucketSize;
    private final long refillRate;

    private long currentBucketSize;
    private long lastRefillTimeStamp;


    public TokenBucket(long maxBucketSize, long refillRate) {
        this.maxBucketSize = maxBucketSize;
        this.refillRate = refillRate;

        this.currentBucketSize = maxBucketSize;
        this.lastRefillTimeStamp = System.nanoTime();
    }

    public synchronized boolean alloweRequest(int tokens) {
        refill();
        if(currentBucketSize >= tokens) {
            currentBucketSize -= tokens;
            return true;
        }
        return false;
    }

    @VisibleForTesting
    private void refill() {
        long now = System.nanoTime();
        long tokensToAdd = (now - lastRefillTimeStamp) * refillRate;
        currentBucketSize = Math.min(maxBucketSize, tokensToAdd + currentBucketSize);
        lastRefillTimeStamp = now;
    }
}
