import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BiPartite {

    private final String name;


    public static void main(String[] args) {
        new BiPartite("Hello");
    }
}
