
package org.asura.csveditor.validation.configuration;

/**
 * Enumeration for format values for type string in JSON Table Schema
 * @see <a href="http://specs.frictionlessdata.io/json-table-schema/">JSON Table Schema</a>
 */
public enum StringFormat {
    DEFAULT(null),
    EMAIL("email"),
    URI("uri"),
    BINARY("binary"),
    UUID("uuid");

    private String externalValue;

    StringFormat(String externalValue) {
        this.externalValue = externalValue;
    }

    public String getExternalValue() {
        return externalValue;
    }

    public static StringFormat fromExternalValue(String externalValue) {
        if (externalValue != null) {
            for (StringFormat value : StringFormat.values()) {
                if (externalValue.equals(value.getExternalValue())) {
                    return value;
                }
            }
        }
        return DEFAULT;
    }
}
