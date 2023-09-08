
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import static org.apache.commons.validator.GenericValidator.maxLength;

/**
 * Checks if the value is shorter or exactly as long as the given max length
 */
public class MaxLengthValidation extends EmptyValueIsValid {

    private int maxLength;

    public MaxLengthValidation(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!maxLength(value, maxLength)) {
            error.add("validation.message.max.length", Integer.toString(maxLength));
        }
    }

    @Override
    public Type getType() {
        return Type.MAX_LENGTH;
    }
}
