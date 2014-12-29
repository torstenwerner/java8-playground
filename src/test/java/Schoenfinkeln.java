import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Schoenfinkeln {
    private final List<String> list = Arrays.asList("Hans", "Nina", "Petz", "Maja");
    private final String FILENAME = "/tmp/test.txt";

    @Test
    public void testJava4() throws Exception {
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    @Test
    public void testJava5() throws Exception {
        for (String s : list) {
            System.out.println(s);
        }
    }

    @Test
    public void testAnonymousClass() throws Exception {
        // higher flexibility
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
    }

    @Test
    public void testJava8() throws Exception {
        // shorter and invoke_dynamic()
        list.forEach((String item) -> { System.out.println(item); });
        list.forEach((String item) -> System.out.println(item));
        list.forEach((item) -> System.out.println(item));
        list.forEach(item -> System.out.println(item));
        list.forEach(System.out::println);
        list.forEach(this::printToSystemOut);

        final Consumer<String> printToSystemOut_ = System.out::println;
        list.forEach(printToSystemOut_);
    }

    // but namespace pollution
    private void printToSystemOut(Object value) {
        System.out.println(value);
    }

    @Test
    public void testHigherOrderFunction() throws Exception {
        list.forEach(printTo(System.out));

        final Function<PrintStream, Consumer<String>> printTo_ = printStream -> value -> printStream.println(value);
        list.forEach(printTo_.apply(System.out));
    }

    private Consumer<String> printTo(PrintStream printStream) {
        return value -> printStream.println(value); // or: printStream::println
    }

    @Test
    public void testCurrying() {
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
