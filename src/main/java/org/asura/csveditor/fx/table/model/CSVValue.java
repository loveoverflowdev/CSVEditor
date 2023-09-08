
package org.asura.csveditor.fx.table.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.asura.csveditor.validation.ValidationError;

/**
 * The csv value represents the value of a single cell.
 * It also knows about the position (row and column)
 * and if the value is valid based on the validator.
 */
public class CSVValue {
    private StringProperty value = new SimpleStringProperty();
    private ValidationError valid;

    /**
     * returns the real value
     * @return the real value
     */
    public String getValue() {
        return value.get();
    }

    /**
     * JavaFX property representation of the real value
     * @return property of real value
     */
    public StringProperty valueProperty() {
        return value;
    }

    /**
     * sets the real value
     * @param value the real value
     */
    public void setValue(String value) {
        this.value.set(value);
    }

    /**
     * returns if the value is valid to the rules of the validator
     * @return
     */
    public ValidationError getValidationError() {
        return valid;
    }

    /**
     * sets the state if a value is valid or not
     * @param valid the validation state
     */
    public void setValidationError(ValidationError valid) {
        this.valid = valid;
    }
}
