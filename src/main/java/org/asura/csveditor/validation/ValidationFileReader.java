
package org.asura.csveditor.validation;

import com.google.gson.GsonBuilder;
import org.asura.csveditor.FileReader;
import org.asura.csveditor.validation.configuration.Field;
import org.asura.csveditor.validation.configuration.ValidationConfiguration;
import org.asura.csveditor.validation.configuration.Type;

import java.io.File;
import java.io.IOException;

/**
 * This class loads the constraints as json config
 */
public class ValidationFileReader implements FileReader<ValidationConfiguration> {

    private ValidationConfiguration config;

    @Override
    public void read(File file) throws IOException {
        config = new GsonBuilder().create().fromJson(new java.io.FileReader(file), ValidationConfiguration.class);
        setDefaults();
    }

    private void setDefaults() {
        for (Field field : config.getFields()) {
            if (field.getType() == null) {
                field.setType(Type.STRING);
            }
            if (field.getType() == Type.DATE) {
                if (field.getFormat() == null) {
                    field.setFormat("yyyy-MM-dd");
                }
            }
            if (field.getType() == Type.DATETIME) {
                if (field.getFormat() == null) {
                    field.setFormat("yyyy-MM-ddThh:mm:ssZ");
                }
            }
            if (field.getType() == Type.TIME) {
                if (field.getFormat() == null) {
                    field.setFormat("hh:mm:ss");
                }
            }
        }
    }

    public ValidationConfiguration getContent() {
        return config;
    }
}
