

package org.asura.csveditor.fx.preferences;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import org.asura.csveditor.fx.FXMLController;
import org.asura.csveditor.preferences.Preferences;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

/**
 * controller for preferences
 */
@Component
public class PreferencesController extends FXMLController {

    @FXML
    private TextField quoteChar;

    @FXML
    private TextField delimiterChar;

    @FXML
    private CheckBox ignoreEmptyLines;

    @FXML
    private ComboBox<String> fileEncoding;

    private String endOfLineSymbols;

    private BooleanProperty valid = new SimpleBooleanProperty(true);


    @Value("${fxml.csveditor.preferences.view}")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileEncoding.getItems().addAll(Charset.availableCharsets().keySet());

        UnaryOperator<TextFormatter.Change> allowOnlyOneCharacter = change -> {
            if (change.isContentChange()) {
                if (change.getControlNewText().length() > 1) {
                    return null;
                }
            }
            return change;
        };
        quoteChar.setTextFormatter(new TextFormatter(allowOnlyOneCharacter));
        quoteChar.textProperty().addListener(observable -> {revalidate();});

        delimiterChar.setTextFormatter(new TextFormatter(allowOnlyOneCharacter));
        delimiterChar.textProperty().addListener(observable -> {revalidate();});
    }

    private void revalidate() {
        valid.setValue(quoteChar.getText().length() <= 1 && delimiterChar.getText().length() == 1);
    }

    public void setCsvPreference(Preferences csvPreference) {
        if (csvPreference.quoteChar() != null) {
            quoteChar.setText(csvPreference.quoteChar().toString());
        } else {
            quoteChar.setText("");
        }
        delimiterChar.setText(Character.toString(csvPreference.delimiterChar()));
        ignoreEmptyLines.setSelected(csvPreference.ignoreEmptyLines());
        endOfLineSymbols = csvPreference.endOfLineSymbols();
    }

    public Preferences getCsvPreference() {
        var quote = quoteChar.getText().length() == 0 ? null : quoteChar.getText().charAt(0);
        return new Preferences(quote, delimiterChar.getText().charAt(0), endOfLineSymbols, ignoreEmptyLines.isSelected());
    }

    public void setFileEncoding(String fileEncoding) {
        this.fileEncoding.setValue(fileEncoding);
    }

    public String getFileEncoding() {
        return fileEncoding.getValue();
    }

    public boolean getValid() {
        return valid.get();
    }

    public BooleanProperty validProperty() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid.set(valid);
    }
}
