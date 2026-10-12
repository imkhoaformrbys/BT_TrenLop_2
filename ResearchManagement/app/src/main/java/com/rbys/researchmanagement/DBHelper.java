package com.rbys.researchmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "QLDK.sqlite";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_THANHVIENNHOM = "tblThanhVienNhom";
    public static final String FIELD_MEMID = "MemID";
    public static final String FIELD_MEMNAME = "MemName";
    public static final String FIELD_GROUPID = "GroupID";
    public static final String FIELD_ROLE = "Role";

    public static final String TABLE_THESIS = "tblThesis";
    public static final String FIELD_THESIS_NAME = "ThesisName";

    public static final String TABLE_NHOMDTAI = "tblNhomDeTai";
    public static final String FIELD_NDT_GROUPID = "GroupID";
    public static final String FIELD_NDT_THESISNAME = "ThesisName";

    ArrayList<String> query_list = new ArrayList<String>();

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String q = "CREATE TABLE " + TABLE_THANHVIENNHOM + " ("
                + FIELD_MEMID + " INTEGER PRIMARY KEY, "
                + FIELD_MEMNAME + " VARCHAR, "
                + FIELD_GROUPID + " INTEGER, "
                + FIELD_ROLE + " VARCHAR)";
        query_list.add(q);

        q = "CREATE TABLE " + TABLE_THESIS + " ("
                + FIELD_GROUPID + " INTEGER PRIMARY KEY, "
                + FIELD_THESIS_NAME + " VARCHAR)";
        query_list.add(q);

        q = "CREATE TABLE " + TABLE_NHOMDTAI + " ("
                + FIELD_NDT_GROUPID + " INTEGER PRIMARY KEY, "
                + FIELD_NDT_THESISNAME + " VARCHAR)";
        query_list.add(q);

        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(1, 'Nguyen Van A', 1, 'Nhom truong')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(2, 'Tran Van B', 1, 'Thanh vien')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(3, 'Le Thi C', 1, 'Thanh vien')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(4, 'Pham Van D', 2, 'Nhom truong')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(5, 'Hoang Thi E', 2, 'Thanh vien')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(6, 'Vo Van F', 2, 'Thanh vien')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(7, 'Dang Van G', 3, 'Nhom truong')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(8, 'Bui Thi H', 3, 'Thanh vien')";
        query_list.add(q);

        q = "INSERT INTO " + TABLE_THESIS + " VALUES(1, 'Ung dung quan ly thu vien')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THESIS + " VALUES(2, 'Ung dung dat mon an truc tuyen')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THESIS + " VALUES(3, 'Ung dung hoc tap truc tuyen')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THESIS + " VALUES(4, 'Ung dung quan ly nhan su')";
        query_list.add(q);
        q = "INSERT INTO " + TABLE_THESIS + " VALUES(5, 'Ung dung ban hang online')";
        query_list.add(q);

        for (int i = 0; i < query_list.size(); i++)
            sqLiteDatabase.execSQL(query_list.get(i));
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NHOMDTAI);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_THANHVIENNHOM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_THESIS);
        onCreate(sqLiteDatabase);
    }
}
