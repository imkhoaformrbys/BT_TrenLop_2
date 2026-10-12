package com.rbys.researchmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ThanhVienNhomDataSource {
    Context context;
    DBHelper dbHelper;
    SQLiteDatabase database;

    public ThanhVienNhomDataSource(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }

    public ThanhVienNhomDataSource open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long themThanhVien(int memID, String memName, int groupID, String role) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FIELD_MEMID, memID);
        contentValues.put(DBHelper.FIELD_MEMNAME, memName);
        contentValues.put(DBHelper.FIELD_GROUPID, groupID);
        contentValues.put(DBHelper.FIELD_ROLE, role);
        return database.insert(DBHelper.TABLE_THANHVIENNHOM, null, contentValues);
    }

    public int xoaThanhVien(int memID) {
        return database.delete(DBHelper.TABLE_THANHVIENNHOM,
                DBHelper.FIELD_MEMID + "=?",
                new String[]{String.valueOf(memID)});
    }

    public int capNhatThanhVien(int memID, String memName, int groupID, String role) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.FIELD_MEMNAME, memName);
        contentValues.put(DBHelper.FIELD_GROUPID, groupID);
        contentValues.put(DBHelper.FIELD_ROLE, role);
        return database.update(DBHelper.TABLE_THANHVIENNHOM, contentValues,
                DBHelper.FIELD_MEMID + "=?",
                new String[]{String.valueOf(memID)});
    }

    public ArrayList<ThanhVienNhom> xuatDSThanhVien() {
        String sql = "SELECT * FROM " + DBHelper.TABLE_THANHVIENNHOM;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        ArrayList<ThanhVienNhom> arrayList = new ArrayList<ThanhVienNhom>();
        while (!cursor.isAfterLast()) {
            ThanhVienNhom tv = new ThanhVienNhom(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3));
            arrayList.add(tv);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<ThanhVienNhom> xuatDSThanhVienTheoNhom(int groupID) {
        String sql = "SELECT * FROM " + DBHelper.TABLE_THANHVIENNHOM
                + " WHERE " + DBHelper.FIELD_GROUPID + " = " + groupID;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        ArrayList<ThanhVienNhom> arrayList = new ArrayList<ThanhVienNhom>();
        while (!cursor.isAfterLast()) {
            ThanhVienNhom tv = new ThanhVienNhom(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getString(3));
            arrayList.add(tv);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Integer> layDSNhom() {
        String sql = "SELECT DISTINCT " + DBHelper.FIELD_GROUPID
                + " FROM " + DBHelper.TABLE_THANHVIENNHOM
                + " ORDER BY " + DBHelper.FIELD_GROUPID;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToFirst();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        while (!cursor.isAfterLast()) {
            arrayList.add(cursor.getInt(0));
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }
}
