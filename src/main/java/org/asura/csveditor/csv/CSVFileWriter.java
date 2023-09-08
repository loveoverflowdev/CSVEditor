

package org.asura.csveditor.csv;

import de.siegmar.fastcsv.writer.CsvWriter;
import de.siegmar.fastcsv.writer.QuoteStrategy;
import org.asura.csveditor.FileWriter;
import org.asura.csveditor.fx.table.model.CSVModel;
import org.asura.csveditor.fx.table.model.CSVRow;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * filewriter for the csv
 */
public class CSVFileWriter extends CSVConfigurable implements FileWriter<CSVModel> {

    private CSVModel model;

    public void setContent(CSVModel model) {
        this.model = model;
    }

    @Override
    public void write(File filename) throws IOException {
        try (var writer = getCsvWriter(filename)){
            writer.writeRow(model.getHeader());
            for(CSVRow row: model.getRows()) {
                writer.writeRow(convertMapFromModel(row));
            }
        }
    }

    private CsvWriter getCsvWriter(File filename) throws IOException {
        var writer = CsvWriter.builder().fieldSeparator(csvPreference.delimiterChar());
        if (csvPreference.quoteChar() != null) {
            writer.quoteCharacter(csvPreference.quoteChar());
            writer.quoteStrategy(QuoteStrategy.ALWAYS);
        }

        return writer.build(filename.toPath(), Charset.forName(fileEncoding));
    }

    /**
     * transforms the column map from CSVValue to a simple Map<String,String>
     * @param row the row to convert
     * @return a simple map for the supercvs writer
     */
    private List<String> convertMapFromModel(CSVRow row) {
        return row.getColumns().values().stream().map(v -> v.get().getValue())
                .collect(toList());
    }
}
