
package org.asura.csveditor.fx.table;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import org.asura.csveditor.fx.table.model.CSVRow;
import org.asura.csveditor.fx.table.model.CSVValue;
import org.asura.csveditor.fx.util.I18nValidationUtil;

import java.util.ResourceBundle;

import static javafx.application.Platform.runLater;
import static org.asura.csveditor.fx.util.ColorConstants.ERROR_COLOR;
import static org.asura.csveditor.fx.util.I18nValidationUtil.getI18nValidatioMessage;

/**
 * cell representation which indicates if a cell is valid and not
 * and allows editing
 */
public class EditableValidationCell extends TableCell<CSVRow, CSVValue> {

    private ValueTextField textField;
    private ResourceBundle resourceBundle;

    public EditableValidationCell(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public void startEdit() {
        super.startEdit();
        setTextField();
        runLater(() -> {
            textField.requestFocus();
            textField.selectAll();
        });
    }

    @Override
    public void cancelEdit() {
        setText(getItem().getValue());
        super.cancelEdit();
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    @Override
    protected void updateItem(CSVValue item, boolean empty) {
        super.updateItem(item, empty);

        if (item == null || item.getValidationError() == null || isEditing()) {
            setStyle("");
            setTooltip(null);
        } else if (item.getValidationError() != null) {
            setStyle("-fx-background-color: derive("+ ERROR_COLOR +", 30%)");
            setTooltip(new Tooltip(I18nValidationUtil.getI18nValidatioMessage(resourceBundle, item.getValidationError())));
        }

        if (item == null || empty) {
            setTextInCell(null);
        } else {
            if (isEditing()) {
                setTextField();
                textField.setValue(item);
            } else {
                setTextInCell(item.getValue());
            }
        }
    }

    private void setTextField() {
        if (textField == null) {
            createTextField();
        }
        setGraphic(textField);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    }

    private void setTextInCell(String text) {
        setGraphic(null);
        setText(text);
        setContentDisplay(ContentDisplay.TEXT_ONLY);
    }

    private void createTextField() {
        textField = new ValueTextField(getItem());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        textField.setOnKeyPressed(t -> {
            if (t.getCode() == KeyCode.ENTER) {
                runLater(() -> commitEdit(textField.getValue()));
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    private static class ValueTextField extends TextField {
        private CSVValue value;

        public ValueTextField(CSVValue value) {
            setValue(value);
        }

        public void setValue(CSVValue value) {
            this.value = value;
            setText(value.getValue());
        }

        public CSVValue getValue() {
            value.setValue(getText());
            return value;
        }

    }
}
