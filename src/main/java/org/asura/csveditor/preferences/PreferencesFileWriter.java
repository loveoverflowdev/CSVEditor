
package org.asura.csveditor.preferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.asura.csveditor.FileWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Save preferences to configuration file
 */
public class PreferencesFileWriter implements FileWriter<Preferences> {

    private Preferences csvPreference;

    public void setContent(Preferences csvPreference) {
        this.csvPreference = csvPreference;
    }

    public void write(File file) throws IOException {
        Map<String, Object> preferences = new HashMap<>();
        preferences.put("quoteChar", csvPreference.quoteChar() == null ? null: csvPreference.quoteChar().toString());
        preferences.put("delimiterChar", Character.toString((char)csvPreference.delimiterChar()));
        preferences.put("endOfLineSymbols", csvPreference.endOfLineSymbols());
        preferences.put("ignoreEmptyLines", csvPreference.ignoreEmptyLines());

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Files.write(file.toPath(), gson.toJson(preferences).getBytes());
    }

}
