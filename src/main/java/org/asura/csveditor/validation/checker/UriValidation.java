
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * checks if the value is a valid uri address
 */
public class UriValidation extends EmptyValueIsValid {

    @Override
    public void check(int row, String value, ValidationError error) {
        try {
            new URI(value);
        } catch (URISyntaxException e) {
            error.add("validation.message.uri");
        }
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}
