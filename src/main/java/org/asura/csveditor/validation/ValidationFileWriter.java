

package org.asura.csveditor.validation;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.asura.csveditor.FileWriter;
import org.asura.csveditor.validation.configuration.ValidationConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * file writer for the validation configuration
 */
public class ValidationFileWriter implements FileWriter<ValidationConfiguration> {

    private ValidationConfiguration validationConfiguration;

    public void setContent(ValidationConfiguration validationConfiguration) {
        this.validationConfiguration = validationConfiguration;
    }

    @Override
    public void write(File file) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Files.write(file.toPath(), gson.toJson(validationConfiguration).getBytes());
    }
}
