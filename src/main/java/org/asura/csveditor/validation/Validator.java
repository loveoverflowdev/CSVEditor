
package org.asura.csveditor.validation;

import org.asura.csveditor.fx.table.model.ColumnValueProvider;
import org.asura.csveditor.validation.configuration.Constraints;
import org.asura.csveditor.validation.configuration.Field;
import org.asura.csveditor.validation.configuration.ValidationConfiguration;
import org.asura.csveditor.validation.checker.*;
import org.asura.csveditor.validation.configuration.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class checks all the validations defined in the
 * Config against a given value
 */
public class Validator {

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // member variables
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ValidationConfiguration validationConfig;
    private ColumnValueProvider columnValueProvider;
    private Map<String, Map<Validation.Type, Validation>> columnValidationMap = new HashMap<>();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // constructors
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * JSON configuration for this validator
     *
     * @param validationConfig
     */
    public Validator(ValidationConfiguration validationConfig, ColumnValueProvider columnValueProvider) {
        this.validationConfig = validationConfig;
        this.columnValueProvider = columnValueProvider;
        initColumnValidations();
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // public methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public boolean needsColumnValidation(String column) {
        Map<Validation.Type, Validation> validationMap = columnValidationMap.get(column);
        if (validationMap != null) {
            return validationMap.containsKey(Validation.Type.UNIQUE);
        }
        return false;

    }


    /**
     * checks if the value is valid for the column configuration
     *
     * @param column the column name
     * @param value  the value to check
     * @return ValidationError with information if valid and if not which getMessage happened
     */
    public ValidationError isValid(Integer row, String column, String value) {
        ValidationError result = null;
        if (hasConfig()) {
            ValidationError error = ValidationError.withLineNumber(row).column(column);
            Map<Validation.Type, Validation> validationMap = columnValidationMap.get(column);
            if (validationMap != null) {
                for (Validation validation: validationMap.values()) {
                    if (validation.canBeChecked(value)) {
                        validation.check(row, value, error);
                    }
                }
            }
            if (!error.isEmpty()) {
                result = error;
            }
        }
        return result;
    }


    public boolean hasConfig() {
        return validationConfig != null;
    }

    public void reinitializeColumn(String column) {
        clear(column);
        initializeColumnWithRules(column);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // private methods
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void add(String column, Validation validation) {
        Map<Validation.Type, Validation> validationMap = columnValidationMap.get(column);
        if (validationMap == null) {
            validationMap = new HashMap<>();
            columnValidationMap.put(column, validationMap);
        }
        validationMap.put(validation.getType(), validation);
    }

    private void remove(String column, Validation.Type type) {
        Map<Validation.Type, Validation> validationMap = columnValidationMap.get(column);
        if (validationMap == null) {
            validationMap = new HashMap<>();
            columnValidationMap.put(column, validationMap);
        }
        validationMap.remove(type);
    }

    private void clear(String column) {
        Map<Validation.Type, Validation> validationMap = columnValidationMap.get(column);
        if (validationMap != null) {
            validationMap.clear();
        }
    }

    private void initColumnValidations() {
        if (hasConfig()) {
            for (Field column : validationConfig.getFields()) {
                initializeColumnWithRules(column);
            }
        }
    }

    private void initializeColumnWithRules(String columnName) {
        if (hasConfig()) {
            for (Field column : validationConfig.getFields()) {
                if (column.getName().equals(columnName)) {
                    initializeColumnWithRules(column);
                    break;
                }
            }
        }

    }

    private void initializeColumnWithRules(Field column) {

        if (column.getType() != null) {
            if (column.getType() == Type.NUMBER) {
                add(column.getName(), new DoubleValidation());
            }

            if (column.getType() == Type.INTEGER) {
                add(column.getName(), new IntegerValidation());
            }

            if (column.getType() == Type.DATE) {
                String format = ValidationFormatHelper.dateFormat(column.getFormat(), "yyyy-MM-dd");
                add(column.getName(), new DateValidation(format));
            }

            if (column.getType() == Type.DATETIME) {
                String format = ValidationFormatHelper.dateFormat(column.getFormat(), "yyyy-MM-ddThh:mm:ssZ");
                add(column.getName(), new DateValidation(format));
            }

            if (column.getType() == Type.TIME) {
                String format = ValidationFormatHelper.dateFormat(column.getFormat(), "hh:mm:ss");
                add(column.getName(), new DateValidation(format));
            }

            if (column.getType() == Type.STRING && column.getFormat() == null) {
                remove(column.getName(), Validation.Type.STRING);
            } else {

                if (column.getType() == Type.STRING && column.getFormat().equalsIgnoreCase(StringFormat.EMAIL.getExternalValue())) {
                    add(column.getName(), new EmailValidation());
                }

                if (column.getType() == Type.STRING && column.getFormat().equalsIgnoreCase(StringFormat.URI.getExternalValue())) {
                    add(column.getName(), new UriValidation());
                }

                if (column.getType() == Type.STRING && column.getFormat().equalsIgnoreCase(StringFormat.UUID.getExternalValue())) {
                    add(column.getName(), new UuidValidation());
                }

                if (column.getType() == Type.STRING && column.getFormat().equalsIgnoreCase(StringFormat.BINARY.getExternalValue())) {
                    add(column.getName(), new BinaryValidation());
                }

            }
        }

        String groovy = column.getGroovy();
        if (groovy != null && !groovy.trim().isEmpty()) {
            add(column.getName(), new GroovyValidation(groovy));
        }

        Constraints constraints = column.getConstraints();
        if (constraints != null) {
            Boolean notEmptyRule = constraints.getRequired();
            if (notEmptyRule != null && notEmptyRule) {
                add(column.getName(), new NotEmptyValidation());
            }

            Boolean uniqueRule = constraints.getUnique();
            if (uniqueRule != null && uniqueRule) {
                add(column.getName(), new UniqueValidation(columnValueProvider, column.getName()));
            }

            Integer minLength = constraints.getMinLength();
            if (minLength != null) {
                add(column.getName(), new MinLengthValidation(minLength));
            }

            Integer maxLength = constraints.getMaxLength();
            if (maxLength != null) {
                add(column.getName(), new MaxLengthValidation(maxLength));
            }

            String regexp = constraints.getPattern();
            if (regexp != null && !regexp.trim().isEmpty()) {
                add(column.getName(), new RegExpValidation(regexp));
            }



            List<String> valueOfRule =  constraints.getEnumeration();
            if (valueOfRule != null && !valueOfRule.isEmpty()) {
                add(column.getName(), new ValueOfValidation(valueOfRule));
            }
        }
    }




    public ValidationError isHeaderValid(String[] headerNames) {
        ValidationError result = null;
        if (validationConfig != null) {
            String[] headerNamesConfig = validationConfig.headerNames();
            if (headerNamesConfig != null) {
                if (headerNames.length != headerNamesConfig.length) {
                    result = ValidationError.withoutLineNumber().add("validation.message.header.length",
                            Integer.toString(headerNames.length),
                            Integer.toString(headerNamesConfig.length));
                    return result;
                }

                ValidationError error = ValidationError.withoutLineNumber();

                for (int i = 0; i < headerNamesConfig.length; i++) {
                    if (!headerNamesConfig[i].equals(headerNames[i])) {
                        error.add("validation.message.header.match",
                                Integer.toString(i),
                                headerNamesConfig[i],
                                headerNames[i]);
                    }
                }
                if (!error.isEmpty()) {
                    result = error;
                }
            }
        }
        return result;
    }


}
