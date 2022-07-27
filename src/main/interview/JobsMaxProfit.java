import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

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
