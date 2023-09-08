
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import static org.apache.commons.validator.GenericValidator.minLength;

/**
 * Checks if the value is at minimum long as the given min length
 */
public class MinLengthValidation extends EmptyValueIsValid {

    private int minLength;

    public MinLengthValidation(int minLength) {
        this.minLength = minLength;
    }

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!minLength(value, minLength)) {
            error.add("validation.message.min.length", Integer.toString(minLength));
        }
    }

    @Override
    public Type getType() {
        return Type.MIN_LENGTH;
    }
}
