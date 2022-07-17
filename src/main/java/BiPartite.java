import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@RequiredArgsConstructor
public class BiPartite {

    private final String name;


    public static void main(String[] args) {
        new BiPartite("Hello");

        final Callable<Void> r = () -> {
            Long start = System.currentTimeMillis();
            final String s1 = "Mohit";
            StringBuilder sb= new StringBuilder(s1);
            StringBuilder stringBuilder = sb.deleteCharAt(3);
            System.out.println("r0" + stringBuilder);
           // stringBuilder.insert(3, s1.charAt(3));
            System.out.println("r0.1" + stringBuilder);
            Long end = System.currentTimeMillis() - start;
            System.out.println("r0.1" + end);
            return null;
        };

        final Callable<Void> r1 = () -> {
            Long start = System.currentTimeMillis();
            final String s1 = "Mohit";
            String prev = s1.substring(0, 3) + s1.substring(3);
            System.out.println("r1" + prev);
            Long end = System.currentTimeMillis() - start;
            System.out.println("r1" + end);
            return null;
        };


        ExecutorService service = Executors.newFixedThreadPool(3);
        List<Callable<Void>> tasks = new java.util.ArrayList<>();
        tasks.add(r1);
        tasks.add(r);
        try {
            service.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
