
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import java.util.List;

import static java.util.stream.Collectors.joining;

/**
 * Checks if the value is part of a list of values
 */
public class ValueOfValidation extends EmptyValueIsValid {

    private List<String> values;

    public ValueOfValidation(List<String> values) {
        this.values = values;
    }

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!values.contains(value)) {
            String commaSeparated = values.stream().collect(joining(", "));
            error.add("validation.message.value.of", value, commaSeparated);
        }
    }

    @Override
    public Type getType() {
        return Type.VALUE_OF;
    }
}
