
package org.asura.csveditor.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.asura.csveditor.fx.about.AboutController;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.Objects;

import static javafx.application.Platform.exit;

@Configuration
@ComponentScan("org.asura")
@PropertySource(value = "classpath:/org/asura/csveditor/fx/application.properties")
public class CSVEditor extends Application {

    private AnnotationConfigApplicationContext appContext;

    @Override
    public void start(Stage primaryStage) throws Exception {
        appContext = new AnnotationConfigApplicationContext(CSVEditor.class);
        String name = appContext.getEnvironment().getProperty("application.name");
        String version = appContext.getEnvironment().getProperty("application.version");

        Platform.setImplicitExit(false);

        AboutController aboutController = appContext.getBean(AboutController.class);
        aboutController.setHostServices(getHostServices());

        try {
            showUI(primaryStage, name, version);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Override
    public void stop() throws Exception {
        if (appContext != null) {
            appContext.close();
        }

        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showUI(Stage primaryStage, String name, String version) {
        CSVEditorController CSVEditorController = appContext.getBean(CSVEditorController.class);
        Scene scene = new Scene((Parent) CSVEditorController.getView());
        var defaultThemeCss = Objects.requireNonNull(getClass().getResource("/org/asura/csveditor/fx/csveditor.css")).toExternalForm();
        scene.getRoot().getStylesheets().add(defaultThemeCss);

        primaryStage.setScene(scene);
        primaryStage.setTitle(String.format("%s %s", name, version));
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(CSVEditor.class.getResourceAsStream("/org/asura/csveditor/icon/logo.png"))));
        primaryStage.show();
        primaryStage.setMaximized(true);

        primaryStage.setOnCloseRequest(event -> {
            if (!CSVEditorController.canExit()) {
                event.consume();
            } else {
                exit();
            }
        });
    }

}
