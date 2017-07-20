package com.github.lwhite1.tablesaw.filtering;

import com.github.lwhite1.tablesaw.api.IntColumn;
import com.github.lwhite1.tablesaw.api.LongColumn;
import com.github.lwhite1.tablesaw.api.Table;
import com.github.lwhite1.tablesaw.columns.ColumnReference;
import com.github.lwhite1.tablesaw.util.Selection;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongSet;

/**
 * Created by lomuroe on 17-7-18.
 */
public class LongIsIn extends ColumnFilter {

    private LongColumn filterColumn;

    public LongIsIn(ColumnReference reference, LongColumn filterColumn) {
        super(reference);
        this.filterColumn = filterColumn;
    }

    public LongIsIn(ColumnReference reference, long... longs) {
        super(reference);
        this.filterColumn = LongColumn.create("temp", new LongArrayList(longs));
    }

    public Selection apply(Table relation) {
        LongColumn LongColumn = (LongColumn) relation.column(columnReference.getColumnName());
        LongSet firstSet = LongColumn.asSet();
        firstSet.retainAll(filterColumn.data());
        return LongColumn.select(firstSet::contains);
    }
}
