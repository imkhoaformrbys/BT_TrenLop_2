# TỔNG HỢP TOÀN BỘ SOURCE CODE JAVA (ResearchManagement)
> **Dự án**: ResearchManagement  
> **Package**: `com.rbys.researchmanagement`  
> **Đường dẫn**: `D:\School\NH_2026-2027\PT_UDDD\BT_TrenLop_2\ResearchManagement\app\src\main\java\com\rbys\researchmanagement`

---

## MỤC LỤC
1. [DBHelper.java](#1-dbhelperjava)
2. [CÁC LỚP THỰC THỂ DATABASE (MODEL)](#2-các-lớp-thực-thể-database-model)
   - [2.1 tblThanhVienNhom.java](#21-tblthanhviennhomjava)
   - [2.2 tblThesis.java](#22-tblthesisjava)
   - [2.3 tblNhomDeTai.java](#23-tblnhomdetaijava)
3. [CÁC LỚP DATASOURCE (TRUY XUẤT DỮ LIỆU)](#3-các-lớp-datasource-truy-xuất-dữ-liệu)
   - [3.1 ThanhVienNhomDataSource.java](#31-thanhviennhomdatasourcejava)
   - [3.2 ThesisDataSource.java](#32-thesisdatasourcejava)
   - [3.3 NhomDeTaiDataSource.java](#33-nhomdetaidatasourcejava)
4. [CÁC LỚP MÀN HÌNH (ACTIVITY)](#4-các-lớp-màn-hình-activity)
   - [4.1 MainActivity.java](#41-mainactivityjava)
   - [4.2 ChonThanhVienActivity.java](#42-chonthanhvienactivityjava)
   - [4.3 ChonNhomActivity.java](#43-chonnhomactivityjava)
   - [4.4 ChonDeTaiActivity.java](#44-chondetaiactivityjava)

---

## 1. DBHelper.java

```java
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

        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(1, 'Nguyen Van A', 1, 'Nhom truong')");
        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(2, 'Tran Van B', 1, 'Thanh vien')");
        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(3, 'Le Thi C', 1, 'Thanh vien')");
        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(4, 'Pham Van D', 2, 'Nhom truong')");
        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(5, 'Hoang Thi E', 2, 'Thanh vien')");
        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(6, 'Vo Van F', 2, 'Thanh vien')");
        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(7, 'Dang Van G', 3, 'Nhom truong')");
        query_list.add("INSERT INTO " + TABLE_THANHVIENNHOM + " VALUES(8, 'Bui Thi H', 3, 'Thanh vien')");

        query_list.add("INSERT INTO " + TABLE_THESIS + " VALUES(1, 'Ung dung quan ly thu vien')");
        query_list.add("INSERT INTO " + TABLE_THESIS + " VALUES(2, 'Ung dung dat mon an truc tuyen')");
        query_list.add("INSERT INTO " + TABLE_THESIS + " VALUES(3, 'Ung dung hoc tap truc tuyen')");
        query_list.add("INSERT INTO " + TABLE_THESIS + " VALUES(4, 'Ung dung quan ly nhan su')");
        query_list.add("INSERT INTO " + TABLE_THESIS + " VALUES(5, 'Ung dung ban hang online')");

        for (int i = 0; i < query_list.size(); i++) {
            sqLiteDatabase.execSQL(query_list.get(i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NHOMDTAI);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_THANHVIENNHOM);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_THESIS);
        onCreate(sqLiteDatabase);
    }
}
```

---

## 2. CÁC LỚP THỰC THỂ DATABASE (MODEL)

### 2.1 tblThanhVienNhom.java

```java
package com.rbys.researchmanagement;

import java.io.Serializable;

public class tblThanhVienNhom implements Serializable {
    private int memID;
    private String memName;
    private int groupID;
    private String role;

    public tblThanhVienNhom() {
    }

    public tblThanhVienNhom(int memID, String memName, int groupID, String role) {
        this.memID = memID;
        this.memName = memName;
        this.groupID = groupID;
        this.role = role;
    }

    public int getMemID() {
        return memID;
    }

    public void setMemID(int memID) {
        this.memID = memID;
    }

    public String getMemName() {
        return memName;
    }

    public void setMemName(String memName) {
        this.memName = memName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return memID + "\t\t" + memName + "\t\t" + role;
    }
}
```

### 2.2 tblThesis.java

```java
package com.rbys.researchmanagement;

import java.io.Serializable;

public class tblThesis implements Serializable {
    private int groupID;
    private String thesisName;

    public tblThesis() {
    }

    public tblThesis(int groupID, String thesisName) {
        this.groupID = groupID;
        this.thesisName = thesisName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getThesisName() {
        return thesisName;
    }

    public void setThesisName(String thesisName) {
        this.thesisName = thesisName;
    }

    @Override
    public String toString() {
        return thesisName;
    }
}
```

### 2.3 tblNhomDeTai.java

```java
package com.rbys.researchmanagement;

import java.io.Serializable;

public class tblNhomDeTai implements Serializable {
    private int groupID;
    private String thesisName;

    public tblNhomDeTai() {
    }

    public tblNhomDeTai(int groupID, String thesisName) {
        this.groupID = groupID;
        this.thesisName = thesisName;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getThesisName() {
        return thesisName;
    }

    public void setThesisName(String thesisName) {
        this.thesisName = thesisName;
    }

    @Override
    public String toString() {
        return "Nhom " + groupID + " - " + thesisName;
    }
}
```

---

## 3. CÁC LỚP DATASOURCE (TRUY XUẤT DỮ LIỆU)

### 3.1 ThanhVienNhomDataSource.java

```java
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
```

### 3.2 ThesisDataSource.java

```java
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
```

### 3.3 NhomDeTaiDataSource.java

```java
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
```

---

## 4. CÁC LỚP MÀN HÌNH (ACTIVITY)

### 4.1 MainActivity.java

```java
package com.rbys.researchmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_CHON_THANHVIEN = 100;
    static final int REQUEST_CHON_NHOM = 101;
    static final int REQUEST_CHON_DETAI = 102;

    TextView tvNhomDuocChon, tvKetQua;
    ListView lvThanhVienNhom;
    Button btnChonThanhVien, btnChonNhom, btnChonDeTai, btnThoat;

    ThanhVienNhomDataSource thanhVienNhomDataSource;
    NhomDeTaiDataSource nhomDeTaiDataSource;

    int selectedNhom = -1;
    String selectedDeTai = "";
    ArrayList<ThanhVienNhom> dsThanhVien;
    ArrayAdapter<ThanhVienNhom> adapterTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvNhomDuocChon = (TextView) findViewById(R.id.tvNhomDuocChon);
        tvKetQua = (TextView) findViewById(R.id.tvKetQua);
        lvThanhVienNhom = (ListView) findViewById(R.id.lvThanhVienNhom);
        btnChonThanhVien = (Button) findViewById(R.id.btnChonThanhVien);
        btnChonNhom = (Button) findViewById(R.id.btnChonNhom);
        btnChonDeTai = (Button) findViewById(R.id.btnChonDeTai);
        btnThoat = (Button) findViewById(R.id.btnThoat);

        thanhVienNhomDataSource = new ThanhVienNhomDataSource(this);
        thanhVienNhomDataSource.open();
        nhomDeTaiDataSource = new NhomDeTaiDataSource(this);
        nhomDeTaiDataSource.open();

        dsThanhVien = new ArrayList<ThanhVienNhom>();
        adapterTV = new ArrayAdapter<ThanhVienNhom>(this,
                android.R.layout.simple_list_item_1, dsThanhVien);
        lvThanhVienNhom.setAdapter(adapterTV);

        capNhatKetQua();

        btnChonThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChonThanhVienActivity.class);
                startActivityForResult(intent, REQUEST_CHON_THANHVIEN);
            }
        });

        btnChonNhom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChonNhomActivity.class);
                startActivityForResult(intent, REQUEST_CHON_NHOM);
            }
        });

        btnChonDeTai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedNhom == -1) {
                    Toast.makeText(MainActivity.this, "Vui long chon nhom truoc", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this, ChonDeTaiActivity.class);
                startActivityForResult(intent, REQUEST_CHON_DETAI);
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thanhVienNhomDataSource.close();
                nhomDeTaiDataSource.close();
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CHON_THANHVIEN:
                if (resultCode == RESULT_OK && data != null) {
                    int groupID = data.getIntExtra("selectedGroupID", -1);
                    if (groupID != -1) {
                        selectedNhom = groupID;
                        tvNhomDuocChon.setText("Nhom " + selectedNhom);
                        hienThiThanhVienNhom();
                    }
                }
                break;
            case REQUEST_CHON_NHOM:
                if (resultCode == RESULT_OK && data != null) {
                    selectedNhom = data.getIntExtra("selectedNhom", -1);
                    tvNhomDuocChon.setText("Nhom " + selectedNhom);
                    hienThiThanhVienNhom();
                    capNhatKetQua();
                }
                break;
            case REQUEST_CHON_DETAI:
                if (resultCode == RESULT_OK && data != null) {
                    selectedDeTai = data.getStringExtra("selectedDeTai");
                    nhomDeTaiDataSource.themNhomDeTai(selectedNhom, selectedDeTai);
                    capNhatKetQua();
                    Toast.makeText(this, "Dang ky thanh cong!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void hienThiThanhVienNhom() {
        dsThanhVien.clear();
        dsThanhVien.addAll(thanhVienNhomDataSource.xuatDSThanhVienTheoNhom(selectedNhom));
        adapterTV.notifyDataSetChanged();
    }

    private void capNhatKetQua() {
        ArrayList<NhomDeTai> dsKetQua = nhomDeTaiDataSource.xuatDSNhomDeTai();
        if (dsKetQua.isEmpty()) {
            tvKetQua.setText("Chua co ket qua dang ky");
        } else {
            StringBuilder sb = new StringBuilder();
            for (NhomDeTai ndt : dsKetQua) {
                sb.append(ndt.toString()).append("\n");
            }
            tvKetQua.setText(sb.toString().trim());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thanhVienNhomDataSource.close();
        nhomDeTaiDataSource.close();
    }
}
```

### 4.2 ChonThanhVienActivity.java

```java
package com.rbys.researchmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ChonThanhVienActivity extends Activity {
    Spinner spnNhom;
    ListView lvDSSinhVien, lvThanhVienNhom;
    Button btnThem, btnLuu, btnQuayLai;
    ThanhVienNhomDataSource thanhVienNhomDataSource;
    ArrayList<ThanhVienNhom> dsSinhVien;
    ArrayList<ThanhVienNhom> dsThanhVienNhom;
    ArrayAdapter<ThanhVienNhom> adapterSV, adapterTV;
    int selectedGroupID = -1;
    int selectedSVPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_thanhvien);

        spnNhom = (Spinner) findViewById(R.id.spnNhomTV);
        lvDSSinhVien = (ListView) findViewById(R.id.lvDSSinhVien);
        lvThanhVienNhom = (ListView) findViewById(R.id.lvThanhVienNhom);
        btnThem = (Button) findViewById(R.id.btnThem);
        btnLuu = (Button) findViewById(R.id.btnLuu);
        btnQuayLai = (Button) findViewById(R.id.btnQuayLai);

        thanhVienNhomDataSource = new ThanhVienNhomDataSource(this);
        thanhVienNhomDataSource.open();

        ArrayList<Integer> dsNhom = thanhVienNhomDataSource.layDSNhom();
        ArrayAdapter<Integer> nhomAdapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, dsNhom);
        nhomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnNhom.setAdapter(nhomAdapter);

        dsSinhVien = thanhVienNhomDataSource.xuatDSThanhVien();
        dsThanhVienNhom = new ArrayList<ThanhVienNhom>();

        adapterSV = new ArrayAdapter<ThanhVienNhom>(this,
                android.R.layout.simple_list_item_1, dsSinhVien);
        adapterTV = new ArrayAdapter<ThanhVienNhom>(this,
                android.R.layout.simple_list_item_1, dsThanhVienNhom);
        lvDSSinhVien.setAdapter(adapterSV);
        lvThanhVienNhom.setAdapter(adapterTV);

        spnNhom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGroupID = (int) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        lvDSSinhVien.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedSVPosition = i;
            }
        });

        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedSVPosition >= 0 && selectedSVPosition < dsSinhVien.size()) {
                    ThanhVienNhom tv = dsSinhVien.get(selectedSVPosition);
                    dsThanhVienNhom.add(tv);
                    dsSinhVien.remove(selectedSVPosition);
                    adapterSV.notifyDataSetChanged();
                    adapterTV.notifyDataSetChanged();
                    selectedSVPosition = -1;
                }
            }
        });

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedGroupID == -1) {
                    Toast.makeText(ChonThanhVienActivity.this, "Vui long chon nhom", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (dsThanhVienNhom.isEmpty()) {
                    Toast.makeText(ChonThanhVienActivity.this, "Chua co thanh vien nao", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (ThanhVienNhom tv : dsThanhVienNhom) {
                    tv.setGroupID(selectedGroupID);
                    thanhVienNhomDataSource.capNhatThanhVien(tv.getMemID(), tv.getMemName(), selectedGroupID, tv.getRole());
                }
                Toast.makeText(ChonThanhVienActivity.this, "Da luu thanh vien vao nhom " + selectedGroupID, Toast.LENGTH_SHORT).show();

                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedGroupID", selectedGroupID);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thanhVienNhomDataSource.close();
    }
}
```

### 4.3 ChonNhomActivity.java

```java
package com.rbys.researchmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ChonNhomActivity extends Activity {
    Spinner spnChonNhom;
    Button btnChon;
    ThanhVienNhomDataSource thanhVienNhomDataSource;
    int selectedNhom = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_nhom);

        spnChonNhom = (Spinner) findViewById(R.id.spnChonNhom);
        btnChon = (Button) findViewById(R.id.btnChon);

        thanhVienNhomDataSource = new ThanhVienNhomDataSource(this);
        thanhVienNhomDataSource.open();

        ArrayList<Integer> dsNhom = thanhVienNhomDataSource.layDSNhom();
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this,
                android.R.layout.simple_spinner_item, dsNhom);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnChonNhom.setAdapter(adapter);

        spnChonNhom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNhom = (int) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedNhom == -1) {
                    Toast.makeText(ChonNhomActivity.this, "Vui long chon nhom", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ChonNhomActivity.this, "Nhom " + selectedNhom + " da dc chon.", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedNhom", selectedNhom);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thanhVienNhomDataSource.close();
    }
}
```

### 4.4 ChonDeTaiActivity.java

```java
package com.rbys.researchmanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ChonDeTaiActivity extends Activity {
    Spinner spnChonDeTai;
    Button btnChon;
    ThesisDataSource thesisDataSource;
    String selectedDeTai = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_detai);

        spnChonDeTai = (Spinner) findViewById(R.id.spnChonDeTai);
        btnChon = (Button) findViewById(R.id.btnChon);

        thesisDataSource = new ThesisDataSource(this);
        thesisDataSource.open();

        ArrayList<String> dsDeTai = thesisDataSource.layDSTenDeTai();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dsDeTai);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnChonDeTai.setAdapter(adapter);

        spnChonDeTai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDeTai = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDeTai.isEmpty()) {
                    Toast.makeText(ChonDeTaiActivity.this, "Vui long chon de tai", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ChonDeTaiActivity.this, "De tai da dc chon: " + selectedDeTai, Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedDeTai", selectedDeTai);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thesisDataSource.close();
    }
}
```
