
package org.asura.csveditor.fx;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.util.ResourceBundle;

public abstract class FXMLController implements InitializingBean, Initializable {

    protected Node view;
    protected String fxmlFilePath;
    protected String resourcePath;

    public abstract void setFxmlFilePath(String filePath);

    @Value("${resource.main}")
    public void setResourceBundle(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadFXML();
    }

    protected final void loadFXML() throws IOException {
        try (InputStream fxmlStream = getClass().getResourceAsStream(fxmlFilePath)) {
            FXMLLoader loader = new FXMLLoader();
            loader.setResources(ResourceBundle.getBundle(this.resourcePath));
            loader.setController(this);
            this.view = (loader.load(fxmlStream));
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public Node getView() {
        return view;
    }
}
