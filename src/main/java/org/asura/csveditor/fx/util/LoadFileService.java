

package org.asura.csveditor.fx.util;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.asura.csveditor.files.FileStorage;

/**
 * Service class for async load of a csv file
 */
@org.springframework.stereotype.Service
public class LoadFileService extends Service<Void> {

    private FileStorage<?> file;

    public void setFileStorage(FileStorage<?> file) {
        this.file = file;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                if (file != null) {
                    file.load();
                }
                return null;
            }
        };
    }
}
