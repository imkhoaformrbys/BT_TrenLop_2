package com.rbys.carbookingmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class HangXeDataSource {
    private Context context;
    private CarBooking_DatabaseHandler dbHelper;
    private SQLiteDatabase database;

    public HangXeDataSource(Context context) {
        this.context = context;
        dbHelper = new CarBooking_DatabaseHandler(context);
    }

    public HangXeDataSource open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long themHangXe(int maHang, String tenHang) {
        ContentValues values = new ContentValues();
        values.put(CarBooking_DatabaseHandler.FIELD_MAHANG, maHang);
        values.put(CarBooking_DatabaseHandler.FIELD_TENHANG, tenHang);
        return database.insert(CarBooking_DatabaseHandler.TABLE_HANGXE, null, values);
    }

    public ArrayList<HangXe> xuatDSHangXe() {
        ArrayList<HangXe> list = new ArrayList<HangXe>();
        String sql = "SELECT " + CarBooking_DatabaseHandler.FIELD_MAHANG + ", "
                + CarBooking_DatabaseHandler.FIELD_TENHANG + " FROM " + CarBooking_DatabaseHandler.TABLE_HANGXE;
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HangXe hangXe = new HangXe(cursor.getInt(0), cursor.getString(1));
                list.add(hangXe);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public String layTenHang(int maHang) {
        String sql = "SELECT " + CarBooking_DatabaseHandler.FIELD_TENHANG
                + " FROM " + CarBooking_DatabaseHandler.TABLE_HANGXE
                + " WHERE " + CarBooking_DatabaseHandler.FIELD_MAHANG + " = " + maHang;
        Cursor cursor = database.rawQuery(sql, null);
        String tenHang = "";
        if (cursor != null && cursor.moveToFirst()) {
            tenHang = cursor.getString(0);
            cursor.close();
        }
        return tenHang;
    }
}
