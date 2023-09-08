
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.validation.ValidationError;

import static org.apache.commons.validator.GenericValidator.isDate;

/**
 * Checks if the date has the right format
 */
public class DateValidation extends EmptyValueIsValid {

    private String dateformat;

    public DateValidation(String dateformat) {
        assert dateformat != null && !dateformat.trim().isEmpty() : "empty date format for date validation";
        this.dateformat = dateformat;
    }

    @Override
    public void check(int row, String value, ValidationError error) {
        if (!isDate(value, dateformat, true)) {
            error.add("validation.message.date.format", dateformat);
        }
    }

    @Override
    public Type getType() {
        return Type.DATE;
    }
}
