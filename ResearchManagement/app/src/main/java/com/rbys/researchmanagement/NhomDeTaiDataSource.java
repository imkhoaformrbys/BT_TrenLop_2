package com.rbys.researchmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NhomDeTaiDataSource {
    Context context;
    DBHelper dbHelper;
    SQLiteDatabase database;

    public NhomDeTaiDataSource(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public NhomDeTaiDataSource open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long themNhomDeTai(int groupID, String thesisName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FIELD_NDT_GROUPID, groupID);
        contentValues.put(DBHelper.FIELD_NDT_THESISNAME, thesisName);
        return database.insert(DBHelper.TABLE_NHOMDTAI, null, contentValues);
    }

    public int xoaNhomDeTai(int groupID) {
        return database.delete(DBHelper.TABLE_NHOMDTAI,
                DBHelper.FIELD_NDT_GROUPID + "=?",
                new String[]{String.valueOf(groupID)});
    }

    public int capNhatNhomDeTai(int groupID, String thesisName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FIELD_NDT_THESISNAME, thesisName);
        return database.update(DBHelper.TABLE_NHOMDTAI, contentValues,
                DBHelper.FIELD_NDT_GROUPID + "=?",
                new String[]{String.valueOf(groupID)});
    }

    public ArrayList<NhomDeTai> xuatDSNhomDeTai() {
        String sql = "SELECT * FROM " + DBHelper.TABLE_NHOMDTAI;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        ArrayList<NhomDeTai> arrayList = new ArrayList<NhomDeTai>();
        while (!cursor.isAfterLast()) {
            NhomDeTai ndt = new NhomDeTai(cursor.getInt(0), cursor.getString(1));
            arrayList.add(ndt);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public NhomDeTai layNhomDeTai(int groupID) {
        String sql = "SELECT * FROM " + DBHelper.TABLE_NHOMDTAI
                + " WHERE " + DBHelper.FIELD_NDT_GROUPID + " = " + groupID;
        Cursor cursor = database.rawQuery(sql, null);
        NhomDeTai ndt = null;
        if (cursor.moveToFirst()) {
            ndt = new NhomDeTai(cursor.getInt(0), cursor.getString(1));
        }
        cursor.close();
        return ndt;
    }
}
