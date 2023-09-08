
package org.asura.csveditor.fx.validation;

import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import org.asura.csveditor.validation.configuration.StringFormat;

import java.util.ResourceBundle;

/**
 * cell factory for string formats
 */
public class StringFormatEditorCellFactory implements Callback<ListView<StringFormat>, ListCell<StringFormat>> {

    private ResourceBundle resourceBundle;

    public StringFormatEditorCellFactory(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    @Override
    public ListCell<StringFormat> call(ListView<StringFormat> param) {
        return new ListCell<StringFormat>(){
            @Override
            protected void updateItem(StringFormat item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setGraphic(null);
                } else {
                    setText(resourceBundle.getString("format.type."+item));
                }
            }
        } ;
    }
}
