package com.rbys.carbookingmanagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CarBooking_DatabaseHandler extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QLDatXe.sqlite";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_HANGXE = "tblHangXe";
    public static final String FIELD_MAHANG = "MaHang";
    public static final String FIELD_TENHANG = "TenHang";

    public static final String TABLE_XE = "tblXe";
    public static final String FIELD_BIENSO = "BienSo";
    public static final String FIELD_NAMSX = "NamSX";
    public static final String FIELD_GIATRI = "GiaTri";
    public static final String FIELD_MAHANG_FK = "MaHang";
    public static final String FIELD_TENKHACH = "TenKhach";
    public static final String FIELD_SDTKHACH = "SDTKhach";

    ArrayList<String> query_list = new ArrayList<String>();

    public CarBooking_DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q = "CREATE TABLE " + TABLE_HANGXE + " ("
                + FIELD_MAHANG + " INTEGER PRIMARY KEY, "
                + FIELD_TENHANG + " VARCHAR UNIQUE)";
        query_list.add(q);

        q = "CREATE TABLE " + TABLE_XE + " ("
                + FIELD_BIENSO + " VARCHAR PRIMARY KEY, "
                + FIELD_NAMSX + " INTEGER, "
                + FIELD_GIATRI + " REAL, "
                + FIELD_MAHANG_FK + " INTEGER REFERENCES " + TABLE_HANGXE + "(" + FIELD_MAHANG + ") ON DELETE CASCADE, "
                + FIELD_TENKHACH + " VARCHAR, "
                + FIELD_SDTKHACH + " VARCHAR)";
        query_list.add(q);

        query_list.add("INSERT INTO " + TABLE_HANGXE + " VALUES(1, 'Toyota')");
        query_list.add("INSERT INTO " + TABLE_HANGXE + " VALUES(2, 'Honda')");
        query_list.add("INSERT INTO " + TABLE_HANGXE + " VALUES(3, 'Mitsubishi')");
        query_list.add("INSERT INTO " + TABLE_HANGXE + " VALUES(4, 'Ford')");
        query_list.add("INSERT INTO " + TABLE_HANGXE + " VALUES(5, 'Hyundai')");

        query_list.add("INSERT INTO " + TABLE_XE + " VALUES('61A32194', 2018, 400.0, 1, '', '')");
        query_list.add("INSERT INTO " + TABLE_XE + " VALUES('64C67598', 2019, 500.0, 3, '', '')");
        query_list.add("INSERT INTO " + TABLE_XE + " VALUES('51A12345', 2020, 650.0, 2, '', '')");

        for (int i = 0; i < query_list.size(); i++) {
            sqLiteDatabase.execSQL(query_list.get(i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_XE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_HANGXE);
        onCreate(sqLiteDatabase);
    }

    public long themXe(String bienSo, int namSX, double giaTri, int maHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_BIENSO, bienSo);
        contentValues.put(FIELD_NAMSX, namSX);
        contentValues.put(FIELD_GIATRI, giaTri);
        contentValues.put(FIELD_MAHANG_FK, maHang);
        contentValues.put(FIELD_TENKHACH, "");
        contentValues.put(FIELD_SDTKHACH, "");
        return db.insert(TABLE_XE, null, contentValues);
    }

    public int suaXe(String bienSo, int namSX, double giaTri, int maHang) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_NAMSX, namSX);
        contentValues.put(FIELD_GIATRI, giaTri);
        contentValues.put(FIELD_MAHANG_FK, maHang);
        return db.update(TABLE_XE, contentValues, FIELD_BIENSO + "=?", new String[]{bienSo});
    }

    public int xoaXe(String bienSo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_XE, FIELD_BIENSO + "=?", new String[]{bienSo});
    }

    public int datXe(String bienSo, String tenKhach, String sdtKhach) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FIELD_TENKHACH, tenKhach);
        contentValues.put(FIELD_SDTKHACH, sdtKhach);
        return db.update(TABLE_XE, contentValues, FIELD_BIENSO + "=?", new String[]{bienSo});
    }

    public ArrayList<Xe> xuatDSXe() {
        ArrayList<Xe> list = new ArrayList<Xe>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT x." + FIELD_BIENSO + ", x." + FIELD_NAMSX + ", x." + FIELD_GIATRI
                + ", x." + FIELD_MAHANG_FK + ", h." + FIELD_TENHANG
                + ", x." + FIELD_TENKHACH + ", x." + FIELD_SDTKHACH
                + " FROM " + TABLE_XE + " x LEFT JOIN " + TABLE_HANGXE + " h ON x." + FIELD_MAHANG_FK + " = h." + FIELD_MAHANG;
        Cursor cursor = db.rawQuery(sql, null);
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

    public ArrayList<HangXe> xuatDSHangXe() {
        ArrayList<HangXe> list = new ArrayList<HangXe>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + FIELD_MAHANG + ", " + FIELD_TENHANG + " FROM " + TABLE_HANGXE;
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                HangXe hx = new HangXe(cursor.getInt(0), cursor.getString(1));
                list.add(hx);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return list;
    }

    public String layTenHang(int maHang) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT " + FIELD_TENHANG + " FROM " + TABLE_HANGXE + " WHERE " + FIELD_MAHANG + " = " + maHang;
        Cursor cursor = db.rawQuery(sql, null);
        String tenHang = "";
        if (cursor != null && cursor.moveToFirst()) {
            tenHang = cursor.getString(0);
            cursor.close();
        }
        return tenHang;
    }
}
