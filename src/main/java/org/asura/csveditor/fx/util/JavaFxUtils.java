package org.asura.csveditor.fx.util;

import javafx.concurrent.Worker;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Alert;
import org.asura.csveditor.fx.CSVEditorController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class helps extract useful messages from exceptions
 * TODO: make I18n maybe?
 */
public final class JavaFxUtils {

    private static final Logger logger = LogManager.getLogger(CSVEditorController.class);

    private JavaFxUtils() {
    }

    public static void onServiceError(WorkerStateEvent event, String windowTitle, String errorHeader) {
        onServiceError(event, windowTitle, errorHeader, () -> {
        });
    }

    public static void onServiceError(WorkerStateEvent event, String windowTitle, String errorHeader, Runnable rollbackAction) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(windowTitle);
        alert.setHeaderText(errorHeader);
        alert.setContentText(findExceptionMessage(event.getSource()));
        alert.showAndWait();
    }

    private static String findExceptionMessage(Worker<?> source) {
        if (source == null) {
            return "Cannot identify the source of the event!";
        } else if (source.getException() == null) {
            return "The event did not have an exception?";
        }
        Throwable exception = source.getException();
        logger.error("Error in " + source.getClass().getSimpleName(), exception);
        if (exception.getMessage() == null) {
            return "Exception of type " + exception.getClass() + " had no message, check the logs.";
        }
        return exception.getMessage();
    }

}
