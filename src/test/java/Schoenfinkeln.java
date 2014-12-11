import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Schoenfinkeln {

    public static final String FILENAME = "/tmp/test.txt";

    public static void main(String args[]) {
        final List<String> list = Arrays.asList("Hans", "Nina", "Petz", "Maja");

        final Function<PrintStream, Consumer<Object>> printTo = printStream -> object -> printStream.println(object);
        list.forEach(printTo.apply(System.out));

        final Function<String, OutputStream> fileOutputStream = name -> {
            try {
                return new FileOutputStream(name);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        };

        final OutputStream outputStream = fileOutputStream.apply(FILENAME);
        final Function<OutputStream, PrintStream> printStreamOf = PrintStream::new;
        list.forEach(printTo.compose(printStreamOf).apply(outputStream));
        list.forEach(printStreamOf.andThen(printTo).apply(outputStream));

        list.forEach(printTo.compose(printStreamOf).compose(fileOutputStream).apply(FILENAME));
        list.forEach(fileOutputStream.andThen(printStreamOf).andThen(printTo).apply(FILENAME));

        list.forEach(fileOutputStream.andThen(printStreamOf).andThen(printTo).apply(FILENAME)
                .andThen(printTo.apply(System.out)));
    }
}
