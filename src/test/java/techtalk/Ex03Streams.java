package techtalk;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.*;
import java.util.stream.Collectors;

public class Ex03Streams {
    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            System.out.printf("\nRunning test: %s\n", description.getMethodName());
        }
    };

    private final List<String> list = Arrays.asList("Nina", "Petz", "Maja", "Hans", "HANS");

    @Test
    public void testForEach() throws Exception {

    }

    @Test
    public void testCollect() throws Exception {
        final String asString = null;
        System.out.println(asString);
    }

    @Test
    public void testToList() throws Exception {
        final List<String> asList = null;
        System.out.println(asList);
    }

    @Test
    public void testToSet() throws Exception {
        final Set<String> asSet = null;
        System.out.println(asSet);
    }

    @Test
    public void testGroupBy() throws Exception {
        final Map<String, Long> asMap = null;
        System.out.println(asMap);
    }
}
