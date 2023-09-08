

package org.asura.csveditor.fx.list;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.WeakListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.asura.csveditor.fx.table.model.CSVModel;
import org.asura.csveditor.validation.ValidationError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.asura.csveditor.fx.util.ColorConstants;
import org.asura.csveditor.fx.util.I18nValidationUtil;
import org.controlsfx.control.PopOver;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER;

/**
 * clickable side bar with error markers
 */
public class ErrorSideBar extends Region {

    private static final Logger logger = LogManager.getLogger(ErrorSideBar.class);

    private static final double WIDTH = 20.0;
    private static final int BORDER = 8;
    private static final double STATUS_BLOCK_HEIGHT = WIDTH - BORDER;
    private static final double STATUS_BLOCK_WIDTH = WIDTH - BORDER;
    private static final double STATUS_BLOCK_OFFSET = WIDTH + BORDER / 2;

    private ListChangeListener<ValidationError> errorListListener = c -> setErrorMarker();
    private WeakListChangeListener<ValidationError> weakErrorListListener = new WeakListChangeListener<>(errorListListener);
    private ObjectProperty<CSVModel> model = new SimpleObjectProperty<>();
    private ObjectProperty<ValidationError> selectedValidationError = new SimpleObjectProperty<>();
    private PopOver popOver = new PopOver();
    private ResourceBundle resourceBundle;
    private Region statusBlock;

    public ErrorSideBar(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
        initPopOver();
        setFixWidth();
        addModelListener();

        statusBlock = new Region();
        statusBlock.setPrefSize(STATUS_BLOCK_WIDTH, STATUS_BLOCK_HEIGHT);
        statusBlock.setLayoutY(BORDER / 2);
        statusBlock.setLayoutX(BORDER / 2);
    }

    private void initPopOver() {
        popOver.setAutoHide(true);
        popOver.setArrowLocation(PopOver.ArrowLocation.RIGHT_CENTER);
    }

    public void setModel(CSVModel model) {
        this.model.set(model);
    }

    public CSVModel getModel() {
        return model.get();
    }

    public ObjectProperty<CSVModel> modelProperty() {
        return model;
    }

    public ValidationError getSelectedValidationError() {
        return selectedValidationError.get();
    }

    public ObjectProperty<ValidationError> selectedValidationErrorProperty() {
        return selectedValidationError;
    }

    public void setSelectedValidationError(ValidationError selectedValidationError) {
        this.selectedValidationError.set(selectedValidationError);
    }

    private void addModelListener() {
        model.addListener((observable, oldValue, newValue) -> {
            newValue.getValidationError().addListener(weakErrorListListener);
            setErrorMarker();
        });
    }

    private void setFixWidth() {
        setMinWidth(WIDTH);
        setPrefWidth(WIDTH);
        setMaxWidth(WIDTH);
    }

    private void setErrorMarker() {
        List<Region> errorMarkerList = new ArrayList<>();
        errorMarkerList.add(statusBlock);
        statusBlock.setStyle("-fx-background-color: " + ColorConstants.OK_COLOR);
        if (model.get() != null) {
            List<ValidationError> errorList = model.get().getValidationError();
            if (errorList != null && !errorList.isEmpty()) {

                statusBlock.setStyle("-fx-background-color: " + ColorConstants.ERROR_COLOR);

                int rows = model.get().getRows().size();
                double space = (double)heightWithoutStatusBlock() / rows;
                errorList.forEach(error -> errorMarkerList.add(generateErrorMarker(space, error)));
            }
        }
        getChildren().setAll(errorMarkerList);
    }

    private int heightWithoutStatusBlock() {
        return (int)(getHeight() - STATUS_BLOCK_OFFSET);
    }

    private Region generateErrorMarker(double space, ValidationError error) {
        logger.info("generate error marker for {} errors in line {}", error.getMessages().size(), error.getLineNumber());
        logger.info("layout y is set to {}", (space * error.getLineNumber() + STATUS_BLOCK_OFFSET));
        Region errorMarker = new Region();
        errorMarker.setLayoutY(space * error.getLineNumber() + STATUS_BLOCK_OFFSET);
        errorMarker.setPrefSize(WIDTH, 2);
        errorMarker.setStyle("-fx-background-color: " + ColorConstants.ERROR_COLOR);
        errorMarker.setOnMouseClicked(event -> selectedValidationError.setValue(error));
        errorMarker.setOnMouseEntered(event -> {
            popOver.setContentNode(popupContent(I18nValidationUtil.getI18nValidatioMessageWithColumn(resourceBundle, error)));
            popOver.show(errorMarker, -16);
        });
        return errorMarker;
    }

    private Node popupContent(String text) {
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10,10,10,10));
        vBox.getChildren().add(new Text(text));
        vBox.setAlignment(CENTER);
        return vBox;
    }

}
