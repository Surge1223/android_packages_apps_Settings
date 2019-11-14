package com.google.android.settings.external;

import android.content.Context;
import android.database.Cursor;

import com.android.settings.datausage.DataUsageSummary;
import com.android.settings.slices.SliceBuilderUtils;
import com.android.settings.slices.SliceData;

public interface Queryable
{
    default String getIntentString(final Context context, final String s, final Class<DataUsageSummary> clazz, String s2) {
        return SliceBuilderUtils.buildSearchResultPageIntent(context, clazz.getName(), s, s2, 1033).toUri(0);
    }

    default Cursor getUpdateCursor(final Context context, final float n) {
        throw new UnsupportedOperationException("Method not supported");
    }

    default Cursor getUpdateCursor(final Context context, final int n) {
        throw new UnsupportedOperationException("Method not supported");
    }

    default Cursor getUpdateCursor(final Context context, SliceData sliceData, String s) {
        try {
            return this.getUpdateCursor(context, Integer.valueOf(s));
        }
        catch (NumberFormatException ex) {
            return this.getUpdateCursor(context, Float.valueOf(s));
        }
    }

    default boolean shouldChangeValue(final int n, final int n2, final int n3) {
        return n == 0 && n2 != n3;
    }

    default boolean shouldChangeValue(final int n, final long n2, final long n3) {
        return n == 0 && n2 != n3;
    }
}

