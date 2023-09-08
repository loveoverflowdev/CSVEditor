
package org.asura.csveditor.fx.validation;

import javafx.util.StringConverter;
import org.asura.csveditor.validation.configuration.StringFormat;

import java.util.ResourceBundle;

/**
 * converter for string formats
 */
public class StringFormatStringConverter extends StringConverter<StringFormat> {

    private ResourceBundle resourceBundle;

    public StringFormatStringConverter(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public String toString(StringFormat item) {
        return resourceBundle.getString("format.type."+item);
    }

    @Override
    public StringFormat fromString(String string) {
        return StringFormat.fromExternalValue(string);
    }
}
