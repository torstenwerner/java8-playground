package techtalk;

import org.junit.Test;

import java.security.MessageDigest;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Ex07StringTest {
    public byte[] hash(String input) throws Exception {
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(input.getBytes("UTF-8"));
    }

    @Test
    public void testHex() throws Exception {
        final byte[] someHash = hash("Hello World!");
        final String hexString = null;

        assertThat(hexString, is("7f 83 b1 65 7f f1 fc 53 b9 2d c1 81 48 a1 d6 5d fc 2d 4b 1f a3 d6 77 28 4a dd d2 0 12 6d 90 69"));
    }
}
