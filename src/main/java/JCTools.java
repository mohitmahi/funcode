import org.jctools.queues.MpmcArrayQueue;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class JCTools {

    public static void main(String[] args) throws InterruptedException {
        Queue<Runnable> queue = createQueue(10000);
        Queue<Runnable> queue1 = new LinkedBlockingQueue<>(10000);


        new Thread(() -> {
            int i = 0;
            while (i++ < 100) {
                int finalI = i;
                queue.add(() -> {
                    System.out.println("TEST1::" + finalI);
                });
                queue1.add(() -> {
                    System.out.println("TEST1::" + finalI);
                });
            }
        }).start();
        new Thread(() -> {
            int i = 0;
            while (i++ < 100) {
                int finalI = i;
                queue.add(() -> {
                    System.out.println("TEST2::" + finalI);
                });
                queue1.add(() -> {
                    System.out.println("TEST2::" + finalI);
                });
            }
        }).start();
        new Thread(() -> {
            int i = 0;
            while (i++ < 100) {
                int finalI = i;
                queue.add(() -> {
                    System.out.println("TEST3::" + finalI);
                });
                queue1.add(() -> {
                    System.out.println("TEST3::" + finalI);
                });
            }
        }).start();

        new Thread(() -> {
            while (queue.peek() != null) {
                Runnable poll = queue.poll();
                poll.run();
            }
            System.out.println("Done-JPQueue");
        }).start();
        new Thread(() -> {
            while (queue1.peek() != null) {
                Runnable poll = queue1.poll();
                poll.run();
            }
            System.out.println("Done-Normal");
        }).start();
    }

    protected static Queue<Runnable> createQueue(int queueCapacity) {

        return new MpmcArrayQueue(queueCapacity);
    }
}
