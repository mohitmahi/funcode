import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileReaderUtil {
    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader("/Users/mpalriwal/workspace/funcode/src/main/java/input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                System.out.println((line.split(" ").length));
                System.out.println(Arrays.stream(line.split(" ")).filter(x -> !x.isBlank()).collect(Collectors.toList()));
                System.out.println(Arrays.stream(line.split(" ")).filter(x -> !x.isBlank()).collect(Collectors.joining("-")));
                Arrays.stream(line.split(" ")).forEach(x -> System.out.print(StringUtils.reverse(x) + " "));
                System.out.println("\n------");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map( i -> i*i).collect(Collectors.toList());
        System.out.println(squaresList);

        StringBuffer buffer=new StringBuffer("hello");
        buffer.append("java");
        System.out.println(buffer);

        StringBuilder builder=new StringBuilder("hello"); // non-thread safe so fast.
        builder.append("java");
        System.out.println(builder);

    }
}
