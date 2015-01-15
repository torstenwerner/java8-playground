package techtalk;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class Ex01Schoenfinkeln {
    private final List<String> list = Arrays.asList("Hans", "Nina", "Petz", "Maja");
    private final String FILENAME = "/tmp/test.txt";

    @Test
    public void testJava4() throws Exception {

    }

    @Test
    public void testJava5() throws Exception {

    }

    @Test
    public void testAnonymousClass() throws Exception {
        // higher flexibility

    }

    @Test
    public void testJava8() throws Exception {
        // shorter and invoke_dynamic()

    }

    private void printToSystemOut(Object value) {
        System.out.println(value);
    }

    @Test
    public void testConsumer() throws Exception {
    }

    @Test
    public void testHigherOrderFunction() throws Exception {
    }

    private Consumer<String> printTo(PrintStream printStream) {
        return null;
    }

    @Test
    public void testCurrying() {

    }
}
