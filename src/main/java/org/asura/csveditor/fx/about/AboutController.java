

package org.asura.csveditor.fx.about;

import javafx.application.HostServices;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import org.asura.csveditor.fx.FXMLController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The about controller
 */
@Component
public class AboutController extends FXMLController {

    private HostServices hostServices;

    @Value("${fxml.csveditor.about.view}")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void openLinkInBrowser(ActionEvent actionEvent) {
        Hyperlink hyperlink = (Hyperlink)actionEvent.getSource();
        hostServices.showDocument(hyperlink.getText());
    }

    public void setHostServices(HostServices hostServices) {
        this.hostServices = hostServices;
    }
}
