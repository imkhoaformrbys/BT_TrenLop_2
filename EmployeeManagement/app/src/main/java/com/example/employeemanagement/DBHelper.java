package com.example.employeemanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QLNV.sqlite";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_NHANVIEN = "tblNhanVien";
    public static final String FIELD_NHANVIEN_MNV = "MNV";
    public static final String FIELD_NHANVIEN_TNV = "TNV";
    public static final String FIELD_LOAINV = "LNV";
    public static final String FIELD_KN = "SNKN";
    public static final String FIELD_CAPBAC = "CAPBAC";
    public static final String FIELD_LOAIKT = "LOAIKT";
    public static final String TABLE_LOAINV = "tblLoaiNhanVien";
    public static final String FIELD_ML = "MaLoai";
    public static final String FIELD_TL = "TenLoai";

    ArrayList<String> query_list = new ArrayList<String>();
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q = "CREATE TABLE tblLoaiNhanVien (MaLoai INTEGER PRIMARY KEY, TenLoai VARCHAR UNIQUE)";
        query_list.add(q);
        q = "INSERT INTO tblLoaiNhanVien VALUES(1,\"Quan ly truong\")";
        query_list.add(q);
        q = "INSERT INTO tblLoaiNhanVien VALUES(2,\"Nhan vien kiem thu\")";
        query_list.add(q);
        q = "INSERT INTO tblLoaiNhanVien VALUES(3,\"Lap trinh vien\")";
        query_list.add(q);
        q = "CREATE TABLE tblNhanVien (MNV INTEGER PRIMARY KEY, TNV VARCHAR, LNV INTEGER REFERENCES tblLoaiNhanVien(MaLoai) ON DELETE CASCADE, CAPBAC INTEGER, LOAIKT VARCHAR, SNKN INTEGER)";
        query_list.add(q);

    for(int i = 0; i < query_list.size();i++)
        sqLiteDatabase.execSQL(query_list.get(i));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_LOAINV);
        sqLiteDatabase.execSQL("drop table if exists " + TABLE_NHANVIEN);
        onCreate(sqLiteDatabase);
    }
}
