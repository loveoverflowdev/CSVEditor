package org.asura.csveditor.csv;

import de.siegmar.fastcsv.reader.NamedCsvReader;
import org.asura.csveditor.FileReader;
import org.asura.csveditor.fx.table.model.CSVModel;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * reads the csv file and stores the values in csv model
 */
public class CSVFileReader extends CSVConfigurable implements FileReader<CSVModel> {

    private CSVModel model;

    @Override
    public void read(File file) throws IOException {

        System.out.println(csvPreference);
        try (var csv = getNamedCsvReader(file)) {
            model = new CSVModel();

            // the header columns are used as the keys to the Map
            var header = csv.getHeader().toArray(new String[csv.getHeader().size()]);
            model.setHeader(header);

            csv.forEach(csvRow -> {
                var row = model.addRow();
                for (String column : header) {
                    model.addValue(row, column, csvRow.getField(column));
                }
            });

        } catch (IOException ex) {
            // TODO perhaps a custom NinjaException that can properly identify and localize the exception message
            // is this a file not found? is this a corrupt csv? etc
            throw new IOException("Failed to read " + file + ": " + ex.getMessage(), ex);
        }
    }

    private NamedCsvReader getNamedCsvReader(File file) throws IOException {
        var builder = NamedCsvReader.builder()
                .fieldSeparator(csvPreference.delimiterChar());
        if (csvPreference.quoteChar() != null) {
            builder.quoteCharacter(csvPreference.quoteChar());
        }

        return builder.build(file.toPath(), Charset.forName(fileEncoding));
    }

    public CSVModel getContent() {
        return model;
    }

}
