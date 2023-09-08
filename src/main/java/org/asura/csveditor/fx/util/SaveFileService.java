package org.asura.csveditor.fx.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.asura.csveditor.files.FileStorage;

/**
 * Service class for async save of a csv file
 */
@org.springframework.stereotype.Service
public class SaveFileService extends Service<Void> {

    private FileStorage<?> file;

    public void setFileStorage(FileStorage<?> value) {
        file = value;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() {
                try {
                    file.save();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        };
    }

}
