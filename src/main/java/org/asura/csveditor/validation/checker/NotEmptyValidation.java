
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import static org.apache.commons.validator.GenericValidator.isBlankOrNull;

/**
 * Checks if the value is not empty
 */
public class NotEmptyValidation implements Validation {

    @Override
    public void check(int row, String value, ValidationError error) {
        if (isBlankOrNull(value)) {
            error.add("validation.message.not.empty");
        }
    }

    @Override
    public Type getType() {
        return Type.NOT_EMPTY;
    }

    @Override
    public boolean canBeChecked(String value) {
        return true;
    }
}
