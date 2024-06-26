import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;

// Similar to LIC: longest increasing sub-seq, but maximize profit not length.
    public class JobsMaxProfit {

    public static void main(String[] args) {

        JobsMaxProfit.Delivery delivery1 = new JobsMaxProfit.Delivery(10, 15, 5);
        JobsMaxProfit.Delivery delivery2 = new JobsMaxProfit.Delivery(16, 19, 5);
        JobsMaxProfit.Delivery delivery3 = new JobsMaxProfit.Delivery(17, 22, 8);
        // delivery1 & delivery3

        List<JobsMaxProfit.Delivery> dList = new ArrayList<>();
        dList.add(delivery1);
        dList.add(delivery2);
        dList.add(delivery3);
        dList.sort(Comparator.comparingLong((JobsMaxProfit.Delivery d) -> d.startTime));

        System.out.println(findMaximumProfit(dList));
        System.out.println(findMaximumProfit_Short(dList));

    }

    static int findMaximumProfit_Short(List<JobsMaxProfit.Delivery> dList) {
        //Sort the jobs by endTime.

        int n = dList.size();
        long[][] jobs = new long[n][3];
        for (int i = 0; i < n; i++) {
            jobs[i] = new long[]{dList.get(i).startTime, dList.get(i).endTime, dList.get(i).pay};
        }
        Arrays.sort(jobs, (a, b) -> (int) (a[1] - b[1])); //sort of end time (increasing)  O(NLogN)
        TreeMap<Integer, Integer> dp = new TreeMap<>(); //<EndTime, Cost> Increasing Order
        dp.put(0, 0); //0->cost with 0-> endTime
        for (long[] job : jobs) {
            // Time O(NlogN) for floorEntry BinarySearch on Red-Black Tree
            int cur = (int) (dp.floorEntry((int) job[0]).getValue() + job[2]); // find an end-time less than equal to startTime using binary-search (Red-black Tree)
            if (cur > dp.lastEntry().getValue()) //lastEntry last end time max recorded value
                dp.put((int) job[1], cur); // new last record with new max_cost and endTime
            //else do nothing (job not selected)
        }
        return dp.lastEntry().getValue(); // max possible recorded at last
    }

    static int findMaximumProfit(List<JobsMaxProfit.Delivery> dList) {
        PriorityQueue<JobsMaxProfit.EndTimeProfit> minPq = new PriorityQueue<>(Comparator.comparingLong((JobsMaxProfit.EndTimeProfit m) -> m.endTime));
        int maxProfit = 0;
        for (final Delivery cDelivery : dList) {
            while (!minPq.isEmpty() && cDelivery.startTime >= minPq.peek().endTime) {
                maxProfit = Math.max(maxProfit, minPq.peek().pay);
                minPq.remove();
            }
            minPq.add(new EndTimeProfit(cDelivery.endTime, cDelivery.pay + maxProfit));
        }
        while (!minPq.isEmpty()) {
            maxProfit = Math.max(maxProfit, minPq.peek().pay);
            minPq.remove();
        }
        return maxProfit;
    }

    static class Delivery {
        long startTime;
        long endTime;
        int pay;

        public Delivery(long s, long e, int p) {
            this.startTime = s;
            this.endTime = e;
            this.pay = p;
        }
    }

    static class EndTimeProfit {
        long endTime;
        int pay;

        public EndTimeProfit(long e, int p) {
            this.endTime = e;
            this.pay = p;
        }
    }
}
