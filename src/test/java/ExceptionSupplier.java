import org.junit.Test;

import java.util.function.Supplier;

public class ExceptionSupplier {
    @Test
    public void testJava7Style() throws Exception {
        final Exception exception = new RuntimeException("some message");
        try {
            System.out.println("The real work is done here.");
            throw exception;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJava8Style() throws Exception {
        final Supplier<Exception> exceptionSupplier = () -> new RuntimeException("some message");
        try {
            System.out.println("The real work is done here.");
            throw exceptionSupplier.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
