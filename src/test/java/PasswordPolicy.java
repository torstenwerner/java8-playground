import org.junit.Test;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PasswordPolicy {
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final long MIN_CATEGORY_COUNT = 3;

    private final List<String> categories = asList("abcdefghijklmnopqrstuvwxyz", "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "0123456789", ",.-;:_#+*!$%&/()=?");

    public boolean isValidPassword(String password) {
        if (password == null || password.length() < MIN_PASSWORD_LENGTH) {
            return false;
        }

        BiPredicate<Integer, String> isInString = (character, string) -> string.indexOf(character) >= 0;
        //BiPredicate<Integer, String> isInString = (character, string) -> string.chars().anyMatch(character::equals);
        IntFunction<IntPredicate> hasCategory = character -> index -> isInString.test(character, categories.get(index));

        ToIntFunction<IntPredicate> findCategoryIndex = predicate -> IntStream.range(0, categories.size())
                .filter(predicate)
                .findFirst()
                .orElse(-1);

        final long categoryCount = password.chars()
                .mapToObj(hasCategory)
                .mapToInt(findCategoryIndex)
                .distinct()
                .count();

        return categoryCount >= MIN_CATEGORY_COUNT;
    }

    @Test
    public void testPolicy() throws Exception {
        assertThat(isValidPassword(null), is(false));
        assertThat(isValidPassword(""), is(false));
        assertThat(isValidPassword("12Hallo"), is(false));
        assertThat(isValidPassword("123hallo"), is(false));
        assertThat(isValidPassword("Höhohoho"), is(true));
        assertThat(isValidPassword("123Hallo"), is(true));
        assertThat(isValidPassword("12.hallo"), is(true));
    }
}
