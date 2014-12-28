import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class CheckOptional {
    // still no collection literals
    final List<String> list = Arrays.asList("test", "hello");

    @Test
    public void testGet() throws Exception {
        final Optional<String> hello = Optional.of("hello");
        assertThat(hello.get(), is("hello"));
        try {
            final Optional<String> empty = Optional.of(null);
            fail();
        } catch (NullPointerException e) {
            //e.printStackTrace();
        }
        final Optional<String> empty = Optional.empty();
        try {
            empty.get();
            fail();
        } catch (NoSuchElementException e) {
            //e.printStackTrace();
        }
    }

    @Test
    public void testOrElse() throws Exception {
        assertThat(lengthOfString("test"), is(4));
        assertThat(lengthOfString(""), is(0));
        assertThat(lengthOfString(null), is(0));
    }

    private int lengthOfString(String string) {
        return Optional.ofNullable(string).map(String::length).orElse(0);
    }

    @Test
    public void testFlatMap() throws Exception {
        final Integer length = Optional.ofNullable("hello")
                .flatMap(this::optionalLengthOfString)
                .get();
        assertThat(length, is(5));
    }

    private Optional<Integer> optionalLengthOfString(String string) {
        return Optional.ofNullable(string).map(String::length);
    }

    @Test
    public void testOrElseThrow() throws Exception {
        final Supplier<Exception> exceptionSupplier = () -> new RuntimeException("optional");
        try {
            list.stream()
                    .filter(item -> item.length() > 10)
                    .findAny()
                    .orElseThrow(exceptionSupplier);
            fail();
        } catch (RuntimeException e) {
            //e.printStackTrace();
            assertThat(e.getMessage(), is("optional"));
        }
    }

    @Test
    public void testOrElseGet() throws Exception {
        final String match = list.stream()
                .filter(item -> item.equals("hi"))
                .findAny()
                .orElseGet(() -> "'hi' not found in " + list.toString());
        assertThat(match, is("'hi' not found in [test, hello]"));
    }

    @Test
    public void testIsPresent() throws Exception {
        final boolean present = list.stream()
                .filter(item -> item.startsWith("he"))
                .findAny()
                .isPresent();
        assertTrue(present);
    }

    @Test
    public void testIfPresent() throws Exception {
        list.stream()
                .filter(item -> item.endsWith("st"))
                .findAny()
                .ifPresent(System.out::println);
    }

    @Test
    public void testFilter() throws Exception {
        final boolean present = Optional.ofNullable("hello")
                .filter(value -> value.startsWith("abc"))
                .isPresent();
        assertThat(present, is(false));
    }
}
