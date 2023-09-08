
package org.asura.csveditor.fx.table;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.asura.csveditor.fx.table.model.CSVRow;
import org.asura.csveditor.fx.table.model.CSVValue;

import java.util.ResourceBundle;

/**
 * cell factory for rendering a value in the cell
 */
public class ValidationCellFactory implements Callback<TableColumn<CSVRow, CSVValue>, TableCell<CSVRow, CSVValue>> {

    private ResourceBundle resourceBundle;

    public ValidationCellFactory(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public TableCell<CSVRow, CSVValue> call(TableColumn<CSVRow, CSVValue> param) {
        return new EditableValidationCell(resourceBundle);
    }
}
