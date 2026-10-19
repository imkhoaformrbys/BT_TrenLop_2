# BÀI THI KIỂM TRA GIỮA KỲ - PHÁT TRIỂN ỨNG DỤNG DI ĐỘNG
## ỨNG DỤNG QUẢN LÝ ĐẶT XE CỦA CỬA HÀNG CHO THUÊ Ô TÔ (CarBookingManagement)

- **Dự án**: CarBookingManagement  
- **Package**: `com.rbys.carbookingmanagement`  
- **Đường dẫn**: `D:\School\NH_2026-2027\PT_UDDD\BT_TrenLop_2\CarBookingManagement`

---

## MỤC LỤC BÀI LÀM THEO YÊU CẦU ĐỀ THI

1. [Tạo lớp CSDL CarBooking_DatabaseHandler kế thừa class SQLiteOpenHelper (1đ)](#1-tạo-lớp-csdl-carbooking_databasehandler-kế-thừa-class-sqliteopenhelper-1đ)
   - [a. Viết Constructor cho class CarBooking_DatabaseHandler](#a-viết-constructor-cho-class-carbooking_databasehandler)
   - [b. Override phương thức onCreate](#b-override-phương-thức-oncreate)
   - [c. Tạo các phương thức truy xuất dữ liệu](#c-tạo-các-phương-thức-truy-xuất-dữ-liệu)
   - [Phụ lục Model & DataSource (HangXe, Xe, XeDataSource)](#phụ-lục-các-lớp-model-và-datasource-bổ-trợ)
2. [Tạo OnCreate của Main Activity (1đ)](#2-tạo-oncreate-của-main-activity-1đ)
3. [Viết phương thức xử lý sự kiện OnClick của button THÊM XE (1.5đ)](#3-viết-phương-thức-xử-lý-sự-kiện-onclick-của-button-thêm-xe-15đ)
4. [Viết phương thức xử lý sự kiện OnClick của button ĐẶT XE (2đ)](#4-viết-phương-thức-xử-lý-sự-kiện-onclick-của-button-đặt-xe-2đ)
5. [Viết phương thức xử lý sự kiện OnClick của button ĐÓNG (0.5đ)](#5-viết-phương-thức-xử-lý-sự-kiện-onclick-của-button-đóng-05đ)
6. [Gắn phương thức xử lý sự kiện cho ListView và Context Menu vào ListView (4đ)](#6-gắn-phương-thức-xử-lý-sự-kiện-cho-listview-và-context-menu-vào-listview-4đ)

---

## 1. Tạo lớp CSDL CarBooking_DatabaseHandler kế thừa class SQLiteOpenHelper (1đ)

File: `CarBooking_DatabaseHandler.java`  
Đường dẫn: `CarBookingManagement/app/src/main/java/com/rbys/carbookingmanagement/CarBooking_DatabaseHandler.java`

```java
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

    // ==========================================================
    // a. Viết Constructor cho class CarBooking_DatabaseHandler
    // ==========================================================
    public CarBooking_DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ==========================================================
    // b. Override phương thức onCreate
    // ==========================================================
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

    // ==========================================================
    // c. Tạo các phương thức truy xuất dữ liệu
    // ==========================================================
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
```

### Phụ lục: Các lớp Model và DataSource bổ trợ

#### Model Xe.java
```java
package com.rbys.carbookingmanagement;

import java.io.Serializable;

public class Xe implements Serializable {
    private String bienSo;
    private int namSX;
    private double giaTri;
    private int maHang;
    private String tenHang;
    private String tenKhach;
    private String sdtKhach;

    public Xe() {}

    public Xe(String bienSo, int namSX, double giaTri, int maHang, String tenHang, String tenKhach, String sdtKhach) {
        this.bienSo = bienSo;
        this.namSX = namSX;
        this.giaTri = giaTri;
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.tenKhach = tenKhach;
        this.sdtKhach = sdtKhach;
    }

    // Getters và Setters
    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }
    public int getNamSX() { return namSX; }
    public void setNamSX(int namSX) { this.namSX = namSX; }
    public double getGiaTri() { return giaTri; }
    public void setGiaTri(double giaTri) { this.giaTri = giaTri; }
    public int getMaHang() { return maHang; }
    public void setMaHang(int maHang) { this.maHang = maHang; }
    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }
    public String getTenKhach() { return tenKhach; }
    public void setTenKhach(String tenKhach) { this.tenKhach = tenKhach; }
    public String getSdtKhach() { return sdtKhach; }
    public void setSdtKhach(String sdtKhach) { this.sdtKhach = sdtKhach; }

    @Override
    public String toString() {
        String hang = (tenHang != null && !tenHang.isEmpty()) ? tenHang : "";
        return hang + " " + bienSo + " " + giaTri;
    }
}
```

#### Model HangXe.java
```java
package com.rbys.carbookingmanagement;

import java.io.Serializable;

public class HangXe implements Serializable {
    private int maHang;
    private String tenHang;

    public HangXe() {}
    public HangXe(int maHang, String tenHang) {
        this.maHang = maHang;
        this.tenHang = tenHang;
    }

    public int getMaHang() { return maHang; }
    public void setMaHang(int maHang) { this.maHang = maHang; }
    public String getTenHang() { return tenHang; }
    public void setTenHang(String tenHang) { this.tenHang = tenHang; }

    @Override
    public String toString() {
        return tenHang;
    }
}
```

---

## 2. Tạo OnCreate của Main Activity (1đ)

Khởi tạo các thành phần giao diện, kết nối Database, load danh sách Hãng xe lên Spinner và danh sách Xe lên ListView, đăng ký Context Menu cho ListView:

```java
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

    // Ánh xạ các View
    edtBienSo = findViewById(R.id.edtBienSo);
    edtNamSX = findViewById(R.id.edtNamSX);
    edtGiaTri = findViewById(R.id.edtGiaTri);
    spnHangXe = findViewById(R.id.spnHangXe);
    btnThemXe = findViewById(R.id.btnThemXe);
    btnDatXe = findViewById(R.id.btnDatXe);
    btnDong = findViewById(R.id.btnDong);
    lvDanhSachXe = findViewById(R.id.lvDanhSachXe);

    // Khởi tạo và mở DataSource
    hangXeDataSource = new HangXeDataSource(this);
    hangXeDataSource.open();

    xeDataSource = new XeDataSource(this);
    xeDataSource.open();

    // Nạp dữ liệu vào Spinner Hãng xe
    dsHangXe = hangXeDataSource.xuatDSHangXe();
    adapterHangXe = new ArrayAdapter<HangXe>(this,
            android.R.layout.simple_spinner_item, dsHangXe);
    adapterHangXe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnHangXe.setAdapter(adapterHangXe);

    // Nạp dữ liệu vào ListView Danh sách xe
    dsXe = xeDataSource.xuatDSXe();
    adapterXe = new ArrayAdapter<Xe>(this,
            android.R.layout.simple_list_item_1, dsXe);
    lvDanhSachXe.setAdapter(adapterXe);

    // Đăng ký Context Menu cho ListView
    registerForContextMenu(lvDanhSachXe);

    // Gắn sự kiện click và long click cho ListView
    lvDanhSachXe.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            selectedPosition = position;
            hienThiThongTinXe(position);
        }
    });

    lvDanhSachXe.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
            selectedPosition = position;
            hienThiThongTinXe(position);
            return false;
        }
    });

    // Gắn sự kiện click các nút
    btnThemXe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            xuLyThemXe();
        }
    });

    btnDatXe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            xuLyDatXe();
        }
    });

    btnDong.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            xuLyDong();
        }
    });
}
```

---

## 3. Viết phương thức xử lý sự kiện OnClick của button THÊM XE (1.5đ)

Lấy dữ liệu từ các ô thông tin (Biển số xe, Năm sản xuất, Giá trị xe, Hãng xe), kiểm tra validation, thêm vào SQLite Database và cập nhật danh sách xe hiển thị trên ListView:

```java
private void xuLyThemXe() {
    String bienSo = edtBienSo.getText().toString().trim();
    String namSXStr = edtNamSX.getText().toString().trim();
    String giaTriStr = edtGiaTri.getText().toString().trim();

    if (bienSo.isEmpty() || namSXStr.isEmpty() || giaTriStr.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin xe!", Toast.LENGTH_SHORT).show();
        return;
    }

    int namSX;
    double giaTri;
    try {
        namSX = Integer.parseInt(namSXStr);
        giaTri = Double.parseDouble(giaTriStr);
    } catch (NumberFormatException e) {
        Toast.makeText(this, "Năm sản xuất hoặc giá trị không hợp lệ!", Toast.LENGTH_SHORT).show();
        return;
    }

    HangXe hangXe = (HangXe) spnHangXe.getSelectedItem();
    int maHang = (hangXe != null) ? hangXe.getMaHang() : 1;

    long result = xeDataSource.themXe(bienSo, namSX, giaTri, maHang);
    if (result != -1) {
        capNhatDanhSachXe();
        Toast.makeText(this, "Thêm xe thành công!", Toast.LENGTH_SHORT).show();
        xoaTrangForm();
    } else {
        Toast.makeText(this, "Thêm xe thất bại (Biển số có thể đã tồn tại)!", Toast.LENGTH_SHORT).show();
    }
}

private void capNhatDanhSachXe() {
    dsXe.clear();
    dsXe.addAll(xeDataSource.xuatDSXe());
    adapterXe.notifyDataSetChanged();
}

private void xoaTrangForm() {
    edtBienSo.setText("");
    edtNamSX.setText("");
    edtGiaTri.setText("");
    edtBienSo.requestFocus();
    selectedPosition = -1;
}
```

---

## 4. Viết phương thức xử lý sự kiện OnClick của button ĐẶT XE (2đ)

Kiểm tra người dùng đã chọn xe từ danh sách chưa, truyền thông tin `Xe` qua `DatXeActivity` (Màn hình BOOKING INFO), nhập tên khách và lưu vào CSDL, hiển thị thông báo đặt thành công theo đúng yêu cầu:

### Code xử lý tại MainActivity.java
```java
private void xuLyDatXe() {
    if (selectedPosition < 0 || selectedPosition >= dsXe.size()) {
        Toast.makeText(this, "Vui lòng chọn 1 xe trên danh sách để đặt!", Toast.LENGTH_SHORT).show();
        return;
    }

    Xe xe = dsXe.get(selectedPosition);
    Intent intent = new Intent(MainActivity.this, DatXeActivity.class);
    intent.putExtra("XE", xe);
    startActivityForResult(intent, REQUEST_CODE_DAT_XE);
}

@Override
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_CODE_DAT_XE) {
        capNhatDanhSachXe();
    }
}
```

### Code Màn hình đặt xe (DatXeActivity.java)
```java
package com.rbys.carbookingmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatXeActivity extends AppCompatActivity {
    private EditText edtTenKhach;
    private Button btnThucHienDatXe, btnQuayLai;
    private XeDataSource xeDataSource;
    private Xe xeDuocChon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datxe);

        edtTenKhach = findViewById(R.id.edtTenKhach);
        btnThucHienDatXe = findViewById(R.id.btnThucHienDatXe);
        btnQuayLai = findViewById(R.id.btnQuayLai);

        xeDataSource = new XeDataSource(this);
        xeDataSource.open();

        xeDuocChon = (Xe) getIntent().getSerializableExtra("XE");
        if (xeDuocChon != null && xeDuocChon.getTenKhach() != null && !xeDuocChon.getTenKhach().isEmpty()) {
            edtTenKhach.setText(xeDuocChon.getTenKhach());
        }

        btnThucHienDatXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKhach = edtTenKhach.getText().toString().trim();
                if (tenKhach.isEmpty()) {
                    Toast.makeText(DatXeActivity.this, "Vui lòng nhập tên khách hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (xeDuocChon != null) {
                    xeDataSource.datXe(xeDuocChon.getBienSo(), tenKhach, "");
                    xeDuocChon.setTenKhach(tenKhach);

                    String msg = "Khách hàng " + tenKhach + " da dat xe "
                            + xeDuocChon.getTenHang() + " "
                            + xeDuocChon.getBienSo() + " "
                            + xeDuocChon.getGiaTri() + ". Saving into database";
                    Toast.makeText(DatXeActivity.this, msg, Toast.LENGTH_LONG).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED_XE", xeDuocChon);
                    setResult(RESULT_OK, resultIntent);
                }
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xeDataSource != null) {
            xeDataSource.close();
        }
    }
}
```

---

## 5. Viết phương thức xử lý sự kiện OnClick của button ĐÓNG (0.5đ)

Đóng kết nối các DataSource và đóng màn hình ứng dụng:

```java
private void xuLyDong() {
    if (xeDataSource != null) {
        xeDataSource.close();
    }
    if (hangXeDataSource != null) {
        hangXeDataSource.close();
    }
    finish();
}
```

---

## 6. Gắn phương thức xử lý sự kiện cho ListView và Context Menu vào ListView (4đ)

### a. Gắn sự kiện chọn item trên ListView (đọc dữ liệu lên các ô thông tin chỉ đọc)
```java
private void hienThiThongTinXe(int position) {
    if (position >= 0 && position < dsXe.size()) {
        Xe xe = dsXe.get(position);
        edtBienSo.setText(xe.getBienSo());
        edtNamSX.setText(String.valueOf(xe.getNamSX()));
        edtGiaTri.setText(String.valueOf(xe.getGiaTri()));

        for (int i = 0; i < dsHangXe.size(); i++) {
            if (dsHangXe.get(i).getMaHang() == xe.getMaHang()) {
                spnHangXe.setSelection(i);
                break;
            }
        }
    }
}
```

### b. Khởi tạo Context Menu (`res/menu/context_menu_xe.xml`)
```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/mnuAdd" android:title="Add" />
    <item android:id="@+id/mnuEdit" android:title="Edit" />
    <item android:id="@+id/mnuDelete" android:title="Delete" />
</menu>
```

```java
@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    getMenuInflater().inflate(R.menu.context_menu_xe, menu);
}
```

### c. Hiện thực chức năng các mục trong Context Menu (Add, Edit, Delete)
```java
@Override
public boolean onContextItemSelected(@NonNull MenuItem item) {
    int itemId = item.getItemId();
    if (itemId == R.id.mnuAdd) {
        xuLyThemXe();
        return true;
    } else if (itemId == R.id.mnuEdit) {
        if (selectedPosition < 0 || selectedPosition >= dsXe.size()) {
            Toast.makeText(this, "Vui lòng chọn xe để sửa!", Toast.LENGTH_SHORT).show();
            return true;
        }
        String bienSo = edtBienSo.getText().toString().trim();
        String namSXStr = edtNamSX.getText().toString().trim();
        String giaTriStr = edtGiaTri.getText().toString().trim();

        if (bienSo.isEmpty() || namSXStr.isEmpty() || giaTriStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin để sửa!", Toast.LENGTH_SHORT).show();
            return true;
        }

        int namSX = Integer.parseInt(namSXStr);
        double giaTri = Double.parseDouble(giaTriStr);
        HangXe hangXe = (HangXe) spnHangXe.getSelectedItem();
        int maHang = (hangXe != null) ? hangXe.getMaHang() : 1;

        int rows = xeDataSource.suaXe(bienSo, namSX, giaTri, maHang);
        if (rows > 0) {
            capNhatDanhSachXe();
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
        }
        return true;
    } else if (itemId == R.id.mnuDelete) {
        if (selectedPosition < 0 || selectedPosition >= dsXe.size()) {
            Toast.makeText(this, "Vui lòng chọn xe để xóa!", Toast.LENGTH_SHORT).show();
            return true;
        }

        Xe xe = dsXe.get(selectedPosition);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Có thật sự muốn xóa xe " + xe.getBienSo() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                xeDataSource.xoaXe(xe.getBienSo());
                dsXe.remove(selectedPosition);
                adapterXe.notifyDataSetChanged();
                xoaTrangForm();
                Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
        return true;
    }
    return super.onContextItemSelected(item);
}
```

---

## 7. GIAO DIỆN XML CHI TIẾT

### `activity_main.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:paddingBottom="12dp"
        android:text="QUẢN LÝ ĐẶT XE CỦA CỬA HÀNG CHO THUÊ Ô TÔ!"
        android:textColor="#0080FF"
        android:textSize="15sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/tvBienSo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Biển số xe:"
        android:textColor="#757575"
        android:textSize="13sp" />

    <EditText
        android:id="@+id/edtBienSo"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="text" />

    <TextView
        android:id="@+id/tvNamSX"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Năm sx (Đời xe):"
        android:textColor="#757575"
        android:textSize="13sp" />

    <EditText
        android:id="@+id/edtNamSX"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="number" />

    <TextView
        android:id="@+id/tvGiaTri"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Giá trị Xe:"
        android:textColor="#757575"
        android:textSize="13sp" />

    <EditText
        android:id="@+id/edtGiaTri"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:ems="10"
        android:inputType="numberDecimal" />

    <TextView
        android:id="@+id/tvHangXe"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Hãng xe:"
        android:textColor="#757575"
        android:textSize="13sp" />

    <Spinner
        android:id="@+id/spnHangXe"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:paddingTop="4dp"
        android:paddingBottom="8dp" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:orientation="horizontal">

        <Button
            android:id="@+id/btnThemXe"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginEnd="4dp"
            android:layout_weight="1"
            android:text="THÊM XE" />

        <Button
            android:id="@+id/btnDatXe"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginEnd="4dp"
            android:layout_weight="1"
            android:text="ĐẶT XE" />

        <Button
            android:id="@+id/btnDong"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_weight="1"
            android:text="ĐÓNG" />
    </LinearLayout>

    <TextView
        android:id="@+id/tvDanhSachXe"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:text="Danh sách xe:"
        android:textColor="#757575"
        android:textSize="13sp" />

    <ListView
        android:id="@+id/lvDanhSachXe"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:clickable="true"
        android:contextClickable="true"
        android:isScrollContainer="true"
        android:longClickable="true"
        android:saveEnabled="true" />

</LinearLayout>
```

### `activity_datxe.xml`
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/mainDatXe"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp"
    tools:context=".DatXeActivity">

    <TextView
        android:id="@+id/tvBookingInfoTitle"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:gravity="center"
        android:paddingBottom="16dp"
        android:text="BOOKING INFO"
        android:textColor="#E91E63"
        android:textSize="18sp"
        android:textStyle="bold" />

    <TextView
        android:id="@+id/tvTenKhachLabel"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Tên khách:"
        android:textColor="#757575"
        android:textSize="14sp" />

    <EditText
        android:id="@+id/edtTenKhach"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="16dp"
        android:ems="10"
        android:hint="Nhập tên khách"
        android:inputType="textPersonName" />

    <Button
        android:id="@+id/btnThucHienDatXe"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginBottom="8dp"
        android:text="ĐẶT XE" />

    <Button
        android:id="@+id/btnQuayLai"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="QUAY LẠI" />

</LinearLayout>
```
