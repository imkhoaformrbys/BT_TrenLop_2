package com.rbys.researchmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ThesisDataSource {
    Context context;
    DBHelper dbHelper;
    SQLiteDatabase database;

    public ThesisDataSource(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public ThesisDataSource open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long themThesis(int groupID, String thesisName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FIELD_GROUPID, groupID);
        contentValues.put(DBHelper.FIELD_THESIS_NAME, thesisName);
        return database.insert(DBHelper.TABLE_THESIS, null, contentValues);
    }

    public int xoaThesis(int groupID) {
        return database.delete(DBHelper.TABLE_THESIS,
                DBHelper.FIELD_GROUPID + "=?",
                new String[]{String.valueOf(groupID)});
    }

    public int capNhatThesis(int groupID, String thesisName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FIELD_THESIS_NAME, thesisName);
        return database.update(DBHelper.TABLE_THESIS, contentValues,
                DBHelper.FIELD_GROUPID + "=?",
                new String[]{String.valueOf(groupID)});
    }

    public ArrayList<Thesis> xuatDSThesis() {
        String sql = "SELECT * FROM " + DBHelper.TABLE_THESIS;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        ArrayList<Thesis> arrayList = new ArrayList<Thesis>();
        while (!cursor.isAfterLast()) {
            Thesis thesis = new Thesis(cursor.getInt(0), cursor.getString(1));
            arrayList.add(thesis);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<String> layDSTenDeTai() {
        String sql = "SELECT " + DBHelper.FIELD_THESIS_NAME + " FROM " + DBHelper.TABLE_THESIS;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        ArrayList<String> arrayList = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }
}
