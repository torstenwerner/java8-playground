package techtalk;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class Ex04CheckOptional {
    final List<String> list = Arrays.asList("test", "hello");

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testOrElse() throws Exception {
        assertThat(lengthOfString("test"), is(4));
        assertThat(lengthOfString(""), is(0));
        assertThat(lengthOfString(null), is(0));
    }

    private int lengthOfString(String string) {
        return -1;
    }

    @Test
    public void testFlatMap() throws Exception {
        final Integer length = null;
        assertThat(length, is(5));
    }

    private Optional<Integer> optionalLengthOfString(String string) {
        return Optional.ofNullable(string).map(String::length);
    }

    @Test
    public void testOrElseThrow() throws Exception {
        final Supplier<Exception> exceptionSupplier = () -> new RuntimeException("optional");
        try {

            fail();
        } catch (RuntimeException e) {
            //e.printStackTrace();
            assertThat(e.getMessage(), is("optional"));
        }
    }

    @Test
    public void testOrElseGet() throws Exception {
        final String match = null;
        assertThat(match, is("'hi' not found in [test, hello]"));
    }

    @Test
    public void testIsPresent() throws Exception {
        final boolean present = false;
        assertTrue(present);
    }

    @Test
    public void testIfPresent() throws Exception {

    }

    @Test
    public void testFilter() throws Exception {
        final boolean present = true;
        assertThat(present, is(false));
    }
}
