
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import java.util.Base64;

/**
 * checks if the value is a base64 encoded string representing binary data
 */
public class BinaryValidation extends EmptyValueIsValid {

    @Override
    public void check(int row, String value, ValidationError error) {
        try {
            Base64.getDecoder().decode(value);
        } catch (IllegalArgumentException e) {
            error.add("validation.message.binary");
        }
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}
