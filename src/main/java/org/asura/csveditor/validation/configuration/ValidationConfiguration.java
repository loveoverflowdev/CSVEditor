

package org.asura.csveditor.validation.configuration;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Configuration based on JSON Table Schema
 * @see <a href="http://specs.frictionlessdata.io/json-table-schema/">JSON Table Schema</a>
 */
public class ValidationConfiguration {

    @SerializedName("fields")
    private Field[] fields;

    public Field[] getFields() {
        return fields;
    }

    public void setFields(Field[] fields) {
        this.fields = fields;
    }

    public Field getFieldConfiguration(String column) {
        for (Field field : fields) {
            if (field.getName().equals(column)) {
                return field;
            }
        }
        return null;
    }

    public String[] headerNames() {
        if (fields != null) {
            List<String> headerNames = new ArrayList<>();
            for (Field field : fields) {
                headerNames.add(field.getName());
            }
            return headerNames.toArray(new String[headerNames.size()]);
        }

        return null;
    }

    public void setHeaderNames(String[] header) {
        fields = new Field[header.length];
        int i = 0;
        for (String headerName: header) {
            fields[i] = new Field();
            fields[i].setName(headerName);
            i++;
        }
    }
}
