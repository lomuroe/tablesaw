package com.github.lwhite1.tablesaw.filtering;

import com.github.lwhite1.tablesaw.api.DateColumn;
import com.github.lwhite1.tablesaw.api.Table;
import com.github.lwhite1.tablesaw.columns.ColumnReference;
import com.github.lwhite1.tablesaw.util.Selection;

/**
 * Created by lomuroe on 2017/7/9.
 */
public class StringBetween extends ColumnFilter {
    private String low;
    private String high;

    public StringBetween(ColumnReference reference, String lowValue, String highValue) {
        super(reference);
        this.low = lowValue;
        this.high = highValue;
    }

    public Selection apply(Table relation) {
        DateColumn column = (DateColumn) relation.column(columnReference.getColumnName());
        Selection matches = column.isAfter(low);
        matches.and(column.isBefore(high));
        return matches;
    }
}