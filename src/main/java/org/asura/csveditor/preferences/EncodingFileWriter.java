
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
 * Save the file encoding to configuration file
 */
public class EncodingFileWriter implements FileWriter<String> {

    private String fileEncoding;

    @Override
    public void setContent(String fileEncoding) {
        this.fileEncoding = fileEncoding;
    }

    @Override
    public void write(File filename) throws IOException {
        Map<String, String> encodings = new HashMap<>();
        encodings.put("fileEncoding", CharsetHelper.getCharsetName(fileEncoding));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Files.write(filename.toPath(), gson.toJson(encodings).getBytes());
    }

}
