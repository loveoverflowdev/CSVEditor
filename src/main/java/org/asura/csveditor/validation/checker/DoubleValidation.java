
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import static org.apache.commons.validator.GenericValidator.isDouble;

/**
 * Checks if the value is a double
 */
public class DoubleValidation extends EmptyValueIsValid {

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!isDouble(value)) {
            error.add("validation.message.double");
        }
    }

    @Override
    public Type getType() {
        return Type.DOUBLE;
    }
}
