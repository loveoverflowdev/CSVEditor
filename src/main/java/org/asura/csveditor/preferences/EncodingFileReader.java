

package org.asura.csveditor.preferences;

import com.google.gson.GsonBuilder;
import org.asura.csveditor.FileReader;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * file reader for the file encoding
 */
public class EncodingFileReader implements FileReader<String> {

    private String fileEncoding;

    public EncodingFileReader() {
        fileEncoding = Charset.defaultCharset().name();
    }

    @Override
    public String getContent() {
        return fileEncoding;
    }

    @Override
    public void read(File filename) throws IOException {
        Map settings = new GsonBuilder().create().fromJson(new java.io.FileReader(filename), HashMap.class);
        if (settings != null) {
            fileEncoding = CharsetHelper.getCharsetName(settings.get("fileEncoding").toString());
        }
    }

}
