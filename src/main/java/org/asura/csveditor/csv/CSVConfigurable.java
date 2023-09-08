

package org.asura.csveditor.csv;

import org.asura.csveditor.preferences.Preferences;

import static org.asura.csveditor.preferences.Preferences.defaultPreferences;

/**
 *
 */
public class CSVConfigurable {

    protected Preferences csvPreference;

    protected String fileEncoding;

    public CSVConfigurable() {
        csvPreference = defaultPreferences();
    }

    public void setCsvPreference(Preferences csvPreference) {
        this.csvPreference = csvPreference;
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

}
