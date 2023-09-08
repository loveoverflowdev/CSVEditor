
package org.asura.csveditor.preferences;

import java.nio.charset.Charset;

/**
 * Helper class for getting charset's name
 */
public class CharsetHelper {

    public static String getCharsetName(String name) {
        try {
            return Charset.forName(name).name();
        } catch (Exception e) {
            return Charset.defaultCharset().name();
        }
    }

}
