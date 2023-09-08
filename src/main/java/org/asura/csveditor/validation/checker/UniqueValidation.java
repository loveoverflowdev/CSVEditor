
package org.asura.csveditor.validation.checker;

import org.asura.csveditor.fx.table.model.ColumnValueProvider;
import org.asura.csveditor.validation.ValidationError;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.sort;
import static java.util.stream.Collectors.joining;

/**
 * Checks if the value is unique in the column
 */
public class UniqueValidation extends EmptyValueIsValid {

    private ColumnValueProvider columnValueProvider;
    private String column;

    public UniqueValidation(ColumnValueProvider columnValueProvider, String column) {
        this.columnValueProvider = columnValueProvider;
        this.column = column;
    }

    @Override
    public void check(int row, String value, ValidationError error) {

        List<Integer> lineNumbers = new ArrayList<>();

        int numberOfRows = columnValueProvider.getNumberOfRows();
        for (int currentRowOfIteration = 0; currentRowOfIteration < numberOfRows; currentRowOfIteration++) {
            String storedValue = columnValueProvider.getValue(currentRowOfIteration, column);

            if (value.equals(storedValue) && currentRowOfIteration != row) {
                lineNumbers.add(currentRowOfIteration + 1); // show not 0 based line numbers to user
            }
        }

        if (!lineNumbers.isEmpty()) {
            if (lineNumbers.size() > 1) {
                sort(lineNumbers);
                error.add("validation.message.uniqueness.multiple", value, lineNumbers.stream().map(Object::toString).collect(joining(", ")));
            } else {
                error.add("validation.message.uniqueness.single", value, lineNumbers.get(0).toString());
            }
        }

    }

    @Override
    public Type getType() {
        return Type.UNIQUE;
    }
}
