import org.junit.Test;

import java.time.LocalDate;
import java.util.stream.Stream;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Date8 {
    public static final LocalDate EXCEL_DAY_ZERO = LocalDate.of(1899, DECEMBER, 30);

    private static int daysBetween(LocalDate first, LocalDate second) {
        if (first.isAfter(second)) {
            return -daysBetween(second, first);
        }
        final int dayDifference = second.getDayOfYear() - first.getDayOfYear();
        final int yearDifference = second.getYear() - first.getYear();
        final int sumOfYearLength = Stream.iterate(first, date -> date.plusYears(1))
                .limit(yearDifference)
                .mapToInt(LocalDate::lengthOfYear)
                .sum();
        return dayDifference + sumOfYearLength;
    }

    @Test
    public void testGetDaysBetween() throws Exception {
        assertThat(daysBetween(EXCEL_DAY_ZERO, LocalDate.of(2015, JANUARY, 2)), is(42006));
        assertThat(daysBetween(EXCEL_DAY_ZERO, LocalDate.of(1815, JANUARY, 5)), is(-31040));
    }
}
