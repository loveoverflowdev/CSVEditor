

package org.asura.csveditor.preferences;

public record Preferences(
        Character quoteChar,
        char delimiterChar,
        String endOfLineSymbols,
        boolean ignoreEmptyLines) {

    public static Preferences defaultPreferences() {
        return new Preferences('\"',',', "\n", true);
    }
}
