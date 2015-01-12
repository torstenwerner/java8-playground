import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Lists {
    @Test
    public void testGetOrDefault() throws Exception {
        final Map<String, Integer> map = new HashMap<>();
        map.put("test", 5);
        assertThat(map.getOrDefault("test", 7), is(5));
        assertThat(map.getOrDefault("foo", 7), is(7));
    }
}
