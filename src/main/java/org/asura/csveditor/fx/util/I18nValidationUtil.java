

package org.asura.csveditor.fx.util;

import org.asura.csveditor.validation.ValidationError;
import org.asura.csveditor.validation.ValidationMessage;

import java.io.StringWriter;
import java.util.List;
import java.util.ResourceBundle;

import static java.text.MessageFormat.format;

/**
 * This class makes validation messages readable in supported languages
 */
public class I18nValidationUtil {

    public static String getI18nValidatioMessage(ResourceBundle resourceBundle, List<ValidationError> errors) {

        StringWriter message = new StringWriter();
        errors.forEach(error -> message.append(getI18nValidatioMessage(resourceBundle, error)).append("\n"));


        if (message.toString().length() != 0) {
            return cutOffLastLineBreak(message.toString());
        }

        return "";
    }

    public static String getI18nValidatioMessageWithColumn(ResourceBundle resourceBundle, ValidationError error) {
        return getI18nValidatioMessage(resourceBundle, error, resourceBundle.getString("column") + " " + error.getColumn() + " : ");
    }

    public static String getI18nValidatioMessage(ResourceBundle resourceBundle, ValidationError error) {
        return getI18nValidatioMessage(resourceBundle, error, "");
    }

    private static String getI18nValidatioMessage(ResourceBundle resourceBundle, ValidationError error, String prefix) {

        List<ValidationMessage> validationMessages = error.getMessages();
        StringWriter message = new StringWriter();
        validationMessages.forEach(validationMessage -> {
            message.append(prefix);
            if (resourceBundle.containsKey(validationMessage.getKey())) {
                String resourceText = resourceBundle.getString(validationMessage.getKey());
                if (validationMessage.getParameters().length > 0) {
                    message.append(format(resourceText, (Object[]) validationMessage.getParameters())).append("\n");
                } else {
                    message.append(resourceText).append("\n");
                }
            } else {
                message.append(validationMessage.getKey()).append("\n");
            }
        });

        if (!validationMessages.isEmpty()) {
            return cutOffLastLineBreak(message.toString());
        }

        return "";
    }

    private static String cutOffLastLineBreak(String message) {
        return message.substring(0, message.length()-1);
    }

}
