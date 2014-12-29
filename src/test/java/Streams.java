import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import java.util.*;
import java.util.stream.Collectors;

public class Streams {
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
        list.stream()
                .forEach(System.out::println);
    }

    @Test
    public void testCollect() throws Exception {
        final String asString = list.stream()
                .filter(value -> value.contains("a"))
                .map(String::toUpperCase)
                .distinct()
                .peek(value -> System.out.printf("peek value: %s\n", value))
                .sorted()
                .collect(Collectors.joining(", "));
        System.out.println(asString);
    }

    @Test
    public void testToList() throws Exception {
        final List<String> asList = list.stream()
                .filter(value -> value.contains("a"))
                .map(String::toUpperCase)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        System.out.println(asList);
    }

    @Test
    public void testToSet() throws Exception {
        final Set<String> asSet = list.stream()
                .map(String::toUpperCase)
                .collect(Collectors.toCollection(TreeSet<String>::new));
        System.out.println(asSet);
    }

    @Test
    public void testGroupBy() throws Exception {
        final Map<String, Long> asMap = list.stream()
                .collect(Collectors.groupingBy(String::toUpperCase, Collectors.counting()));
        System.out.println(asMap);
    }
}
