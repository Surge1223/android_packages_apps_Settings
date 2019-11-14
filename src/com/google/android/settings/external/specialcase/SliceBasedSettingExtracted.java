package com.google.android.settings.external.specialcase;

import android.content.Context;
import android.database.Cursor;

import com.android.settings.slices.SliceData;
import com.google.android.settings.external.Queryable;

interface SliceBasedSettingExtracted extends Queryable {
    Cursor getAccessCursor(Context context, SliceData sliceData);

    Cursor getUpdateCursor(Context context, SliceData sliceData, int i);
}

