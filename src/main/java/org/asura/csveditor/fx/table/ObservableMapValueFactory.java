
package org.asura.csveditor.fx.table;

import javafx.beans.property.ObjectProperty;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import org.asura.csveditor.fx.table.model.CSVRow;
import org.asura.csveditor.fx.table.model.CSVValue;

/**
 * cell value factory for the columns in the tableview
 */
public class ObservableMapValueFactory implements
        Callback<TableColumn.CellDataFeatures<CSVRow, CSVValue>, ObjectProperty<CSVValue>> {

    private final Object key;

    public ObservableMapValueFactory(Object key) {
        this.key = key;
    }

    @Override
    public ObjectProperty<CSVValue> call(TableColumn.CellDataFeatures<CSVRow, CSVValue> features) {
        CSVRow row = features.getValue();
        return row.getColumns().get(key);
    }
}
