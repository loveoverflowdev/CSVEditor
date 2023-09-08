
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

/**
 * Interface for all validations
 */
public interface Validation {

    enum Type { NOT_EMPTY, UNIQUE, DOUBLE, INTEGER, MIN_LENGTH, MAX_LENGTH, DATE, REGEXP, VALUE_OF, STRING, GROOVY }
    void check(int row, String value, ValidationError error);
    Type getType();
    boolean canBeChecked(String value);
}
