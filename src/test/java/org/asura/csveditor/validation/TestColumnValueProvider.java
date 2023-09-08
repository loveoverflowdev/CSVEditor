package org.asura.csveditor.validation;

import org.asura.csveditor.fx.table.model.ColumnValueProvider;

/**
 * @author abi
 */
public class TestColumnValueProvider implements ColumnValueProvider {
    @Override
    public String getValue(int row, String column) {
        return null;
    }

    @Override
    public int getNumberOfRows() {
        return 0;
    }
}
