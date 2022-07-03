import org.jctools.queues.MessagePassingQueue;
import org.jctools.queues.MpscArrayQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class JCTools {


    public static void main(String[] args) throws InterruptedException {

        int cnt = 49;
        int i = 0;
        Queue<Runnable> jcQueue = JCUtil.createQueue(10_000);
        Queue<Runnable> linkedBlockingQueue = new ArrayBlockingQueue<>(10_000);
        while (i++ < 50) {
            int finalI = i;
            jcQueue.add(() -> {
                System.out.println("jcQueue::" + finalI);
            });
            linkedBlockingQueue.add(() -> {
                System.out.println("linkedBlockingQueue::" + finalI);
            });
        }
        System.out.println("linkedBlockingQueue:: " + linkedBlockingQueue.size());
        System.out.println("jcQueue:: " + jcQueue.size());

        Runnable task1 = () -> {
            final List<Runnable> batch = new ArrayList<>(cnt);
            System.out.println("BatchSize(Start):: " + batch.size());
            long start = System.currentTimeMillis();

            JCUtil.drain(jcQueue, cnt, (Runnable span) -> batch.add(span));

            long duration = System.currentTimeMillis() - start;
            System.out.println("BatchSize(End) jcQueue:: " + batch.size());
            System.out.println("jcQueue duration:: " + duration);
        };

        Runnable task2 = () -> {
            final List<Runnable> batch = new ArrayList<>(cnt);
            System.out.println("BatchSize(Start):: " + batch.size());
            long start = System.currentTimeMillis();

            JCUtil.drain(linkedBlockingQueue, cnt, (Runnable span) -> batch.add(span));

            long duration = System.currentTimeMillis() - start;
            System.out.println("BatchSize(End) linkedBlockingQueue :: " + batch.size());
            System.out.println("linkedBlockingQueue duration:: " + duration);
        };

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(task1);
        executorService.submit(task2);
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdown();
    }

    static class JCUtil {
        protected static MpscArrayQueue<Runnable> createQueue(int queueCapacity) {
            return new MpscArrayQueue(queueCapacity);
        }

        @SuppressWarnings("unchecked")
        public static <Tspan> void drain(Queue<Tspan> queue, int maxExportBatchSize, Consumer<Tspan> consumer) {
            if (queue instanceof MessagePassingQueue) {
                ((MessagePassingQueue<Tspan>) queue).drain((span) -> consumer.accept(span), maxExportBatchSize);
            } else {
                int polledCount = 0;
                Tspan t;
                while (polledCount++ < maxExportBatchSize && (t = queue.poll()) != null) {
                    consumer.accept(t);
                }
            }
        }
    }
}
