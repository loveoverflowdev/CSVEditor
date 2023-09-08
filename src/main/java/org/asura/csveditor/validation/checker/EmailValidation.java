
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;
import org.apache.commons.validator.routines.EmailValidator;

/**
 * checks if the value is a valid email address
 */
public class EmailValidation extends EmptyValueIsValid {

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!EmailValidator.getInstance().isValid(value)) {
            error.add("validation.message.email");
        }
    }

    @Override
    public Type getType() {
        return Type.STRING;
    }
}
