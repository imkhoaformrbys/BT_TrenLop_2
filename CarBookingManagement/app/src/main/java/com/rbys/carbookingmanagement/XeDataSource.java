package com.rbys.carbookingmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class XeDataSource {
    private Context context;
    private CarBooking_DatabaseHandler dbHelper;
    private SQLiteDatabase database;

    public XeDataSource(Context context) {
        this.context = context;
        dbHelper = new CarBooking_DatabaseHandler(context);
    }

    public XeDataSource open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        database.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long themXe(String bienSo, int namSX, double giaTri, int maHang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarBooking_DatabaseHandler.FIELD_BIENSO, bienSo);
        contentValues.put(CarBooking_DatabaseHandler.FIELD_NAMSX, namSX);
        contentValues.put(CarBooking_DatabaseHandler.FIELD_GIATRI, giaTri);
        contentValues.put(CarBooking_DatabaseHandler.FIELD_MAHANG_FK, maHang);
        contentValues.put(CarBooking_DatabaseHandler.FIELD_TENKHACH, "");
        contentValues.put(CarBooking_DatabaseHandler.FIELD_SDTKHACH, "");
        return database.insert(CarBooking_DatabaseHandler.TABLE_XE, null, contentValues);
    }

    public int suaXe(String bienSo, int namSX, double giaTri, int maHang) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarBooking_DatabaseHandler.FIELD_NAMSX, namSX);
        contentValues.put(CarBooking_DatabaseHandler.FIELD_GIATRI, giaTri);
        contentValues.put(CarBooking_DatabaseHandler.FIELD_MAHANG_FK, maHang);
        return database.update(CarBooking_DatabaseHandler.TABLE_XE, contentValues,
                CarBooking_DatabaseHandler.FIELD_BIENSO + "=?",
                new String[]{bienSo});
    }

    public int xoaXe(String bienSo) {
        return database.delete(CarBooking_DatabaseHandler.TABLE_XE,
                CarBooking_DatabaseHandler.FIELD_BIENSO + "=?",
                new String[]{bienSo});
    }

    public int datXe(String bienSo, String tenKhach, String sdtKhach) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarBooking_DatabaseHandler.FIELD_TENKHACH, tenKhach);
        contentValues.put(CarBooking_DatabaseHandler.FIELD_SDTKHACH, sdtKhach);
        return database.update(CarBooking_DatabaseHandler.TABLE_XE, contentValues,
                CarBooking_DatabaseHandler.FIELD_BIENSO + "=?",
                new String[]{bienSo});
    }

    public ArrayList<Xe> xuatDSXe() {
        ArrayList<Xe> list = new ArrayList<Xe>();
        String sql = "SELECT x." + CarBooking_DatabaseHandler.FIELD_BIENSO
                + ", x." + CarBooking_DatabaseHandler.FIELD_NAMSX
                + ", x." + CarBooking_DatabaseHandler.FIELD_GIATRI
                + ", x." + CarBooking_DatabaseHandler.FIELD_MAHANG_FK
                + ", h." + CarBooking_DatabaseHandler.FIELD_TENHANG
                + ", x." + CarBooking_DatabaseHandler.FIELD_TENKHACH
                + ", x." + CarBooking_DatabaseHandler.FIELD_SDTKHACH
                + " FROM " + CarBooking_DatabaseHandler.TABLE_XE + " x LEFT JOIN "
                + CarBooking_DatabaseHandler.TABLE_HANGXE + " h ON x."
                + CarBooking_DatabaseHandler.FIELD_MAHANG_FK + " = h."
                + CarBooking_DatabaseHandler.FIELD_MAHANG;

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                Xe xe = new Xe(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getDouble(2),
                        cursor.getInt(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6)
                );
                list.add(xe);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }
}
