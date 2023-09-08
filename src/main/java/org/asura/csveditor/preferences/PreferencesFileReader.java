

package org.asura.csveditor.preferences;

import com.google.gson.GsonBuilder;
import org.asura.csveditor.FileReader;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.asura.csveditor.preferences.Preferences.defaultPreferences;

/**
 * file reader for the preferences
 */
public class PreferencesFileReader implements FileReader<Preferences> {

    private Preferences csvPreference;

    public PreferencesFileReader() {
        csvPreference = defaultPreferences();
    }

    @Override
    public void read(File filename) throws IOException {
        Map config = new GsonBuilder().create().fromJson(new java.io.FileReader(filename), HashMap.class);

        if (config != null) {
            Character quoteChar = config.get("quoteChar") == null ? null : config.get("quoteChar").toString().charAt(0);
            char delimiterChar = config.get("delimiterChar").toString().charAt(0);
            String endOfLineSymbols = config.get("endOfLineSymbols").toString();
            boolean ignoreEmptyLines = (Boolean) config.get("ignoreEmptyLines");
            csvPreference = new Preferences(quoteChar, delimiterChar, endOfLineSymbols, ignoreEmptyLines);
        }
    }

    public Preferences getContent() {
        return csvPreference;
    }


}
