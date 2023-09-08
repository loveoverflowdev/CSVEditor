

package org.asura.csveditor.validation;

import java.util.Arrays;

/**
 * Stores the i18n key and possible parameters for validation messages
 */
public class ValidationMessage {

    private String key;
    private String[] parameters;

    public ValidationMessage(String key, String... parameters) {
        this.key = key;
        this.parameters = parameters;
    }

    public String getKey() {
        return key;
    }

    public String[] getParameters() {
        return parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationMessage that = (ValidationMessage) o;

        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(parameters, that.parameters);

    }

    @Override
    public int hashCode() {
        int result = key != null ? key.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }

    @Override
    public String toString() {
        return "ValidationMessage{" +
                "key='" + key + '\'' +
                ", parameters=" + Arrays.toString(parameters) +
                '}';
    }
}
