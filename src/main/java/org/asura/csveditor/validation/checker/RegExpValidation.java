
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import static org.apache.commons.validator.GenericValidator.matchRegexp;

/**
 * Checks the value against the given reg exp
 */
public class RegExpValidation extends EmptyValueIsValid {

    private String regexp;

    public RegExpValidation(String regexp) {
        this.regexp = regexp;
    }

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!matchRegexp(value, regexp)) {
            error.add("validation.message.regexp", regexp);
        }
    }

    @Override
    public Type getType() {
        return Type.REGEXP;
    }
}
