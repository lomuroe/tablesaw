package com.github.lwhite1.tablesaw.filtering.text;

import com.github.lwhite1.tablesaw.api.CategoryColumn;
import com.github.lwhite1.tablesaw.api.Table;
import com.github.lwhite1.tablesaw.columns.Column;
import com.github.lwhite1.tablesaw.columns.ColumnReference;
import com.github.lwhite1.tablesaw.filtering.ColumnFilter;
import com.github.lwhite1.tablesaw.util.Selection;

/**
 * Created by lomuroe on 2017/7/10.
 */
public class TextLessThanOrEqual extends ColumnFilter {

    private String value;

    public TextLessThanOrEqual(ColumnReference reference, String value) {
        super(reference);
        this.value = value;
    }

    @Override
    public Selection apply(Table relation) {
        Column column = relation.column(columnReference().getColumnName());
        CategoryColumn textColumn = (CategoryColumn) column;
        return textColumn.isLessThanOrEqual(value);
    }
}
