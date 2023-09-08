
package org.asura.csveditor.fx.table.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

/**
 * This class represents a single row in the csv file.
 */
public class CSVRow {
    private ObservableMap<String, ObjectProperty<CSVValue>> columns = FXCollections.observableHashMap();
    private int rowNumber;

    /**
     * sets the row number
     * @param rowNumber
     */
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * return the row number
     * @return row number
     */
    public int getRowNumber() {
        return rowNumber;
    }


    /**
     * returns the columns with data as Map
     * @return columns with data
     */
    public ObservableMap<String, ObjectProperty<CSVValue>> getColumns() {
        return columns;
    }

    /**
     * stores the given value in the given column of this row
     * @param column column name
     * @param value the value to store
     */
    CSVValue addValue(String column, String value) {
        CSVValue v = new CSVValue();
        v.setValue(value);
        columns.put(column, new SimpleObjectProperty<>(v));
        return v;
    }

}
