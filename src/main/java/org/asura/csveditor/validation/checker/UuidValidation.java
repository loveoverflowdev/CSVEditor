
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import java.util.UUID;

/**
 * checks if the value is a valid uuid
 */
public class UuidValidation extends EmptyValueIsValid {

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void check(int row, String value, ValidationError error) {
        try {
            UUID.fromString(value);
        } catch (IllegalArgumentException e) {
            error.add("validation.message.uuid");
        }
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}
