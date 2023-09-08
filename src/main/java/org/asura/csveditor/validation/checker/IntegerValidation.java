
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import static org.apache.commons.validator.GenericValidator.isInt;

/**
 * Checks if the value is an integer
 */
public class IntegerValidation extends EmptyValueIsValid {

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!isInt(value)) {
            error.add("validation.message.integer");
        }
    }

    @Override
    public Type getType() {
        return Type.INTEGER;
    }
}
