
package org.asura.csveditor.preferences;

import org.junit.jupiter.api.Test;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class CharsetHelperTest {

    @Test
    public void getCharsetName_known_charset() {
        String result = CharsetHelper.getCharsetName("UTF-16");
        assertEquals(StandardCharsets.UTF_16.name(), result);
        assertNotEquals(StandardCharsets.UTF_16.name(), Charset.defaultCharset());
    }

    @Test
    public void getCharsetName_unknown_charset() {
        String result = CharsetHelper.getCharsetName("foobar");
        assertEquals(Charset.defaultCharset().name(), result);
    }

    @Test
    public void getCharsetName_null_charset() {
        String result = CharsetHelper.getCharsetName(null);
        assertEquals(Charset.defaultCharset().name(), result);
    }

}
