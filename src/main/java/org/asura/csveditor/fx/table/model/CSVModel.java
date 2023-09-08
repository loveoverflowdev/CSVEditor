
package org.asura.csveditor.fx.table.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.asura.csveditor.validation.RevalidationService;
import org.asura.csveditor.validation.configuration.ValidationConfiguration;
import org.asura.csveditor.validation.ValidationError;
import org.asura.csveditor.validation.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The CSVModel is the client representation for the csv filepath.
 * It holds the data in rows, stores the header and manages the validator.
 */
public final class CSVModel implements ColumnValueProvider {

    private static final Logger logger = LogManager.getLogger(CSVModel.class);

    private Validator validator;
    private ObservableList<CSVRow> rows = FXCollections.observableArrayList();
    private String[] header;
    private ObservableList<ValidationError> validationError = FXCollections.observableArrayList();
    private RevalidationService revalidationService = new RevalidationService();

    /**
     * sets the validator configuration for the data revalidates
     *
     * @param validationConfiguration the validator configuration for this data
     */
    public void setValidationConfiguration(ValidationConfiguration validationConfiguration) {
        this.validator = new Validator(validationConfiguration, this);
        revalidate();
    }

    /**
     * returns the data as a list of rows of the
     *
     * @return list of rows
     */
    public ObservableList<CSVRow> getRows() {
        return rows;
    }

    public ObservableList<ValidationError> getValidationError() {
        return validationError;
    }

    /**
     * adds a new and empty row
     *
     * @return the new row
     */
    public CSVRow addRow() {
        CSVRow row = new CSVRow();
        row.setRowNumber(rows.size());
        rows.add(row);
        return row;
    }

    public void addValue(final CSVRow row, final String column, final String value) {
        final CSVValue csvValue = row.addValue(column, value);
        csvValue.valueProperty().addListener(observable -> {
            if (validator.needsColumnValidation(column)) {
                revalidate();
            } else {
                csvValue.setValidationError(validator.isValid(row.getRowNumber(), column, csvValue.getValue()));
            }
        });
    }

    /**
     * sets the column headers as string array
     *
     * @param header the headers of the columns
     */
    public void setHeader(String[] header) {
        this.header = header;
        revalidate();
    }

    /**
     * returns the column headers
     *
     * @return the column headers
     */
    public String[] getHeader() {
        return header;
    }


    public void revalidate(String column) {
        if (!hasValidator()) return;
        validator.reinitializeColumn(column);
        revalidate();
    }

    /**
     * walks through the data and validates each value
     */
    public void revalidate() {
        validationError.clear();

        logger.info("revalidate: hasValidator -> {}", hasValidator());

        if (!hasValidator()) return;
        revalidationService.setHeader(header);
        revalidationService.setRows(rows);
        revalidationService.setValidator(validator);
        revalidationService.setOnSucceeded(t -> validationError.setAll(revalidationService.getValue()));
        revalidationService.setOnFailed(t -> logger.error("revalidation service failed!"));
        revalidationService.restart();
    }

    private boolean hasValidator() {
        return validator != null && validator.hasConfig();
    }

    public ValidationConfiguration createValidationConfiguration() {
        ValidationConfiguration newValidationConfiguration = new ValidationConfiguration();
        newValidationConfiguration.setHeaderNames(this.header);
        this.validator = new Validator(newValidationConfiguration, this);
        this.revalidate();
        return newValidationConfiguration;
    }


    @Override
    public String getValue(int row, String column) {
        return rows.get(row).getColumns().get(column).getValue().getValue();
    }

    @Override
    public int getNumberOfRows() {
        return rows.size();
    }
}
