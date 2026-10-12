package com.example.employeemanagement;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class LoaiNVDataSource {
    Context context;
    DBHelper dbHelper;
    SQLiteDatabase db;

    public LoaiNVDataSource(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
    }
    public LoaiNVDataSource open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        db.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }
    public void close()
    {
        dbHelper.close();
    }
    public int getMLNhanVien(String tenloai)
    {
        String sql = "select " + DBHelper.FIELD_ML + " from " + DBHelper.TABLE_LOAINV + " where " + DBHelper.FIELD_TL + " LIKE '%" + tenloai + "%'" ;
        Cursor cursor = db.rawQuery(sql,null);
        cursor.moveToFirst();
        int kq = 0;
        while (!cursor.isAfterLast())
        {
            kq = cursor.getInt(0);
            cursor.moveToNext();
        }
        cursor.close();
        return kq;
    }
}
