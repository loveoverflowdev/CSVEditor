
package org.asura.csveditor.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds all the error messages
 * for a single cell and the information in
 * which row the cell is
 */
public class ValidationError {

    private final List<ValidationMessage> messages = new ArrayList<>();
    private final Integer lineNumber;
    private String column = "";

    private ValidationError(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public static ValidationError withLineNumber(int lineNumber) {
        return new ValidationError(lineNumber);
    }

    public static ValidationError withoutLineNumber() {
        return new ValidationError(-1);
    }

    public ValidationError column(String column) {
        this.column = column;
        return this;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public String getColumn() {
        return column;
    }

    public List<ValidationMessage> getMessages() {
        return messages;
    }

    public ValidationError add(String key, String... parameters) {
        messages.add(new ValidationMessage(key, parameters));
        return this;
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }
}
