package com.example.employeemanagement;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class NhanVienDataSource {
    Context context;
    DBHelper dbHelper;
    SQLiteDatabase database;
    LoaiNVDataSource loaiNVDataSource;

    public NhanVienDataSource(Context context) {
        this.context = context;
        dbHelper = new DBHelper(context);
        loaiNVDataSource = new LoaiNVDataSource(context);
    }
    public NhanVienDataSource open() throws SQLException
    {
        loaiNVDataSource.open();
        database = dbHelper.getWritableDatabase();
        database.execSQL("PRAGMA foreign_keys=ON;");
        return this;
    }
    public void close()
    {
        dbHelper.close();
    }
    public int xoaNhanVien(int mnv)
    {
       return (database.delete(dbHelper.TABLE_NHANVIEN,new String(dbHelper.FIELD_NHANVIEN_MNV + "=?"), new String[]{String.valueOf(mnv)}));
    }
    public ArrayList<NhanVien> xuatdsNhanVien()
    {
        String sql = "SELECT * FROM tblNhanVien";
        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();
        ArrayList<NhanVien> arrayList = new ArrayList<NhanVien>();
        while (!cursor.isAfterLast())
        {
            switch (cursor.getInt(2)) {
                case 1:
                    QuanLy quanLy = new QuanLy(cursor.getInt(0), cursor.getString(1), cursor.getInt(3));
                    arrayList.add(quanLy);
                    break;
                case 2:
                    NhanVienKiemThu kiemThu = new NhanVienKiemThu(cursor.getInt(0), cursor.getString(1), cursor.getString(4));
                    arrayList.add(kiemThu);
                    break;
                case 3:
                    Developer laptrinhvien = new Developer(cursor.getInt(0), cursor.getString(1), cursor.getInt(5));
                    arrayList.add(laptrinhvien);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }
    public ArrayList<NhanVien> xuatdsNhanVienTheoLoaiNV(int lnv)
    {
        String sql = "SELECT * FROM tblNhanVien WHERE LNV = " + lnv;
        Cursor cursor = database.rawQuery(sql,null);
        cursor.moveToFirst();
        ArrayList<NhanVien> arrayList = new ArrayList<NhanVien>();
        while (!cursor.isAfterLast())
        {
            switch (cursor.getInt(2)) {
                case 1:
                    QuanLy quanLy = new QuanLy(cursor.getInt(0), cursor.getString(1), cursor.getInt(3));
                    arrayList.add(quanLy);
                    break;
                case 2:
                    NhanVienKiemThu kiemThu = new NhanVienKiemThu(cursor.getInt(0), cursor.getString(1), cursor.getString(4));
                    arrayList.add(kiemThu);
                    break;
                case 3:
                    Developer laptrinhvien = new Developer(cursor.getInt(0), cursor.getString(1), cursor.getInt(5));
                    arrayList.add(laptrinhvien);
            }
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }
    public long themNVLaptrinh(int mnv, String ten, int snkn)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.FIELD_NHANVIEN_MNV,mnv);
        contentValues.put(dbHelper.FIELD_NHANVIEN_TNV,ten);
        contentValues.put(dbHelper.FIELD_LOAINV,loaiNVDataSource.getMLNhanVien("Lap trinh vien"));
        contentValues.put(dbHelper.FIELD_KN,snkn);

    return database.insert(dbHelper.TABLE_NHANVIEN,null,contentValues);

    }
    public long themNVQuanly(int mnv, String ten, int capbac)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.FIELD_NHANVIEN_MNV,mnv);
        contentValues.put(dbHelper.FIELD_NHANVIEN_TNV,ten);
        contentValues.put(dbHelper.FIELD_LOAINV,loaiNVDataSource.getMLNhanVien("Quan ly"));
        contentValues.put(dbHelper.FIELD_CAPBAC,capbac);

        return database.insert(dbHelper.TABLE_NHANVIEN,null,contentValues);
    }
    public long themNVKiemThu(int mnv, String ten, String loaikt)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.FIELD_NHANVIEN_MNV,mnv);
        contentValues.put(dbHelper.FIELD_NHANVIEN_TNV,ten);
        contentValues.put(dbHelper.FIELD_LOAINV,loaiNVDataSource.getMLNhanVien("Nhan vien kiem thu"));
        contentValues.put(dbHelper.FIELD_LOAIKT,loaikt);

        return database.insert(dbHelper.TABLE_NHANVIEN,null,contentValues);
    }

    public int suaNVLapTrinh(int mnv, String hoten, int sonam) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.FIELD_NHANVIEN_TNV,hoten);
        contentValues.put(dbHelper.FIELD_KN,sonam);
        return (database.update(dbHelper.TABLE_NHANVIEN,contentValues,dbHelper.FIELD_NHANVIEN_MNV + "=?" ,new String[] {String.valueOf(mnv)}));
    }

    public int suaNVTesting(int mnv, String hoten, String loaikt) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.FIELD_NHANVIEN_TNV,hoten);
        contentValues.put(dbHelper.FIELD_LOAIKT,loaikt);
        return (database.update(dbHelper.TABLE_NHANVIEN,contentValues,dbHelper.FIELD_NHANVIEN_MNV + "=?" ,new String[] {String.valueOf(mnv)}));
    }

    public int suaNVQuanly(int mnv, String hoten, int capbac) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbHelper.FIELD_NHANVIEN_TNV,hoten);
        contentValues.put(dbHelper.FIELD_CAPBAC,capbac);
        return (database.update(dbHelper.TABLE_NHANVIEN,contentValues,dbHelper.FIELD_NHANVIEN_MNV + "=?" ,new String[] {String.valueOf(mnv)}));
    }
}
