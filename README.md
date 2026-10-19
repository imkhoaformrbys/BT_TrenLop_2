# TỔNG HỢP TOÀN BỘ SOURCE CODE JAVA ĐẠT 10 ĐIỂM
## ĐỀ THI GIỮA KỲ: QUẢN LÝ ĐẶT XE CỦA CỬA HÀNG CHO THUÊ Ô TÔ (CarBookingManagement)

> **Dự án**: CarBookingManagement  
> **Package**: `com.rbys.carbookingmanagement`  
> *(Lưu ý: Không bao gồm các file layout XML vì giao diện sẽ được vẽ/kéo-thả lại khi thực hành).*

---

## MỤC LỤC
1. [HƯỚNG DẪN CHI TIẾT THEO BAREM ĐIỂM ĐỀ THI (10 ĐIỂM)](#1-hướng-dẫn-chi-tiết-theo-barem-điểm-đề-thi-10-điểm)
   - [Câu 1: Lớp CSDL CarBooking_DatabaseHandler (1.0đ)](#câu-1-lớp-csdl-carbooking_databasehandler-10đ)
   - [Câu 2: Phương thức onCreate của MainActivity (1.0đ)](#câu-2-phương-thức-oncreate-của-mainactivity-10đ)
   - [Câu 3: Xử lý sự kiện OnClick button THÊM XE (1.5đ)](#câu-3-xử-lý-sự-kiện-onclick-button-thêm-xe-15đ)
   - [Câu 4: Xử lý sự kiện OnClick button ĐẶT XE & DatXeActivity (2.0đ)](#câu-4-xử-lý-sự-kiện-onclick-button-đặt-xe--datxeactivity-20đ)
   - [Câu 5: Xử lý sự kiện OnClick button ĐÓNG (0.5đ)](#câu-5-xử-lý-sự-kiện-onclick-button-đóng-05đ)
   - [Câu 6: Gắn sự kiện ListView và Context Menu (4.0đ)](#câu-6-gắn-sự-kiện-listview-và-context-menu-40đ)
2. [SOURCE CODE ĐẦY ĐỦ NGUYÊN FILE TỪNG LỚP JAVA](#2-source-code-đầy-đủ-nguyên-file-từng-lớp-java)
   - [2.1 CarBooking_DatabaseHandler.java](#21-carbooking_databasehandlerjava)
   - [2.2 Xe.java (Model)](#22-xejava-model)
   - [2.3 HangXe.java (Model)](#23-hangxejava-model)
   - [2.4 MainActivity.java](#24-mainactivityjava)
   - [2.5 DatXeActivity.java](#25-datxeactivityjava)
   - [2.6 XeDataSource.java](#26-xedatasourcejava)
   - [2.7 HangXeDataSource.java](#27-hangxedatasourcejava)
3. [FILE CẤU HÌNH BỔ TRỢ](#3-file-cấu-hình-bổ-trợ)
   - [3.1 context_menu_xe.xml (res/menu)](#31-context_menu_xexml-resmenu)
   - [3.2 AndroidManifest.xml](#32-androidmanifestxml)

---

## 1. HƯỚNG DẪN CHI TIẾT THEO BAREM ĐIỂM ĐỀ THI (10 ĐIỂM)

### Câu 1: Lớp CSDL CarBooking_DatabaseHandler (1.0đ)
Kế thừa `SQLiteOpenHelper`, quản lý 2 bảng `tblHangXe` và `tblXe`.

- **a. Constructor (0.25đ):**
```java
public CarBooking_DatabaseHandler(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
}
```

- **b. Override onCreate (0.25đ):**  
Tạo 2 bảng `tblHangXe` (MaHang, TenHang) và `tblXe` (BienSo, NamSX, GiaTri, MaHang, TenKhach, SDTKhach), đồng thời chèn dữ liệu mẫu ban đầu:
```java
@Override
public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String q = "CREATE TABLE tblHangXe (MaHang INTEGER PRIMARY KEY, TenHang VARCHAR UNIQUE)";
    sqLiteDatabase.execSQL(q);

    q = "CREATE TABLE tblXe (BienSo VARCHAR PRIMARY KEY, NamSX INTEGER, GiaTri REAL, "
            + "MaHang INTEGER REFERENCES tblHangXe(MaHang) ON DELETE CASCADE, "
            + "TenKhach VARCHAR, SDTKhach VARCHAR)";
    sqLiteDatabase.execSQL(q);

    // Chèn dữ liệu hãng xe
    sqLiteDatabase.execSQL("INSERT INTO tblHangXe VALUES(1, 'Toyota')");
    sqLiteDatabase.execSQL("INSERT INTO tblHangXe VALUES(2, 'Honda')");
    sqLiteDatabase.execSQL("INSERT INTO tblHangXe VALUES(3, 'Mitsubishi')");
    sqLiteDatabase.execSQL("INSERT INTO tblHangXe VALUES(4, 'Ford')");
    sqLiteDatabase.execSQL("INSERT INTO tblHangXe VALUES(5, 'Hyundai')");

    // Chèn dữ liệu xe mẫu (khớp với hình đề bài)
    sqLiteDatabase.execSQL("INSERT INTO tblXe VALUES('61A32194', 2018, 400.0, 1, '', '')");
    sqLiteDatabase.execSQL("INSERT INTO tblXe VALUES('64C67598', 2019, 500.0, 3, '', '')");
    sqLiteDatabase.execSQL("INSERT INTO tblXe VALUES('51A12345', 2020, 650.0, 2, '', '')");
}
```

- **c. Các phương thức truy xuất dữ liệu (0.5đ):**  
Gồm thêm, sửa, xóa, đặt xe và truy vấn danh sách:
```java
public long themXe(String bienSo, int namSX, double giaTri, int maHang);
public int suaXe(String bienSo, int namSX, double giaTri, int maHang);
public int xoaXe(String bienSo);
public int datXe(String bienSo, String tenKhach, String sdtKhach);
public ArrayList<Xe> xuatDSXe();
public ArrayList<HangXe> xuatDSHangXe();
```

---

### Câu 2: Phương thức onCreate của MainActivity (1.0đ)
Khởi tạo View, kết nối CSDL, load dữ liệu hãng xe vào Spinner, nạp danh sách xe vào ListView và đăng ký Context Menu:
```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // 1. Ánh xạ View
    edtBienSo = findViewById(R.id.edtBienSo);
    edtNamSX = findViewById(R.id.edtNamSX);
    edtGiaTri = findViewById(R.id.edtGiaTri);
    spnHangXe = findViewById(R.id.spnHangXe);
    btnThemXe = findViewById(R.id.btnThemXe);
    btnDatXe = findViewById(R.id.btnDatXe);
    btnDong = findViewById(R.id.btnDong);
    lvDanhSachXe = findViewById(R.id.lvDanhSachXe);

    // 2. Mở kết nối CSDL
    hangXeDataSource = new HangXeDataSource(this);
    hangXeDataSource.open();
    xeDataSource = new XeDataSource(this);
    xeDataSource.open();

    // 3. Đổ dữ liệu Hãng xe lên Spinner
    dsHangXe = hangXeDataSource.xuatDSHangXe();
    adapterHangXe = new ArrayAdapter<HangXe>(this, android.R.layout.simple_spinner_item, dsHangXe);
    adapterHangXe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spnHangXe.setAdapter(adapterHangXe);

    // 4. Đổ dữ liệu Xe lên ListView
    dsXe = xeDataSource.xuatDSXe();
    adapterXe = new ArrayAdapter<Xe>(this, android.R.layout.simple_list_item_1, dsXe);
    lvDanhSachXe.setAdapter(adapterXe);

    // 5. Đăng ký Context Menu cho ListView
    registerForContextMenu(lvDanhSachXe);

    // 6. Gắn các Listener
    lvDanhSachXe.setOnItemClickListener((parent, view, position, id) -> {
        selectedPosition = position;
        hienThiThongTinXe(position);
    });

    lvDanhSachXe.setOnItemLongClickListener((parent, view, position, id) -> {
        selectedPosition = position;
        hienThiThongTinXe(position);
        return false;
    });

    btnThemXe.setOnClickListener(v -> xuLyThemXe());
    btnDatXe.setOnClickListener(v -> xuLyDatXe());
    btnDong.setOnClickListener(v -> xuLyDong());
}
```

---

### Câu 3: Xử lý sự kiện OnClick button THÊM XE (1.5đ)
Đọc dữ liệu từ form, kiểm tra hợp lệ, thêm vào CSDL và cập nhật ListView:
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
```

---

### Câu 4: Xử lý sự kiện OnClick button ĐẶT XE & DatXeActivity (2.0đ)
- Khi ấn **ĐẶT XE**, kiểm tra xe đã chọn trên ListView, truyền đối tượng `Xe` sang màn hình 2 (`DatXeActivity`).
- Tại màn hình 2, người dùng nhập tên khách, lưu vào CSDL và hiện Toast: `"Khách hàng <ten> da dat xe <hang> <bienso> <giatri>. Saving into database"`. Nút **QUAY LẠI** kết thúc màn hình.

**Tại MainActivity.java:**
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

**Tại DatXeActivity.java:**
```java
btnThucHienDatXe.setOnClickListener(v -> {
    String tenKhach = edtTenKhach.getText().toString().trim();
    if (tenKhach.isEmpty()) {
        Toast.makeText(this, "Vui lòng nhập tên khách hàng!", Toast.LENGTH_SHORT).show();
        return;
    }

    if (xeDuocChon != null) {
        xeDataSource.datXe(xeDuocChon.getBienSo(), tenKhach, "");
        xeDuocChon.setTenKhach(tenKhach);

        String msg = "Khách hàng " + tenKhach + " da dat xe "
                + xeDuocChon.getTenHang() + " "
                + xeDuocChon.getBienSo() + " "
                + xeDuocChon.getGiaTri() + ". Saving into database";
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        Intent resultIntent = new Intent();
        resultIntent.putExtra("UPDATED_XE", xeDuocChon);
        setResult(RESULT_OK, resultIntent);
    }
});

btnQuayLai.setOnClickListener(v -> finish());
```

---

### Câu 5: Xử lý sự kiện OnClick button ĐÓNG (0.5đ)
Đóng kết nối các DataSource và đóng Activity:
```java
private void xuLyDong() {
    if (xeDataSource != null) xeDataSource.close();
    if (hangXeDataSource != null) hangXeDataSource.close();
    finish();
}
```

---

### Câu 6: Gắn sự kiện ListView và Context Menu (4.0đ)
- **Đọc thông tin xe lên các ô khi chọn item (1.0đ):**
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

- **Tạo Context Menu (1.0đ):**
```java
@Override
public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
    super.onCreateContextMenu(menu, v, menuInfo);
    getMenuInflater().inflate(R.menu.context_menu_xe, menu);
}
```

- **Hiện thực chức năng menu item (Add - Edit - Delete) (2.0đ):**
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
        int namSX = Integer.parseInt(edtNamSX.getText().toString().trim());
        double giaTri = Double.parseDouble(edtGiaTri.getText().toString().trim());
        HangXe hangXe = (HangXe) spnHangXe.getSelectedItem();
        int maHang = (hangXe != null) ? hangXe.getMaHang() : 1;

        int rows = xeDataSource.suaXe(bienSo, namSX, giaTri, maHang);
        if (rows > 0) {
            capNhatDanhSachXe();
            Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
        }
        return true;
    } else if (itemId == R.id.mnuDelete) {
        if (selectedPosition < 0 || selectedPosition >= dsXe.size()) {
            Toast.makeText(this, "Vui lòng chọn xe để xóa!", Toast.LENGTH_SHORT).show();
            return true;
        }

        Xe xe = dsXe.get(selectedPosition);
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận")
                .setMessage("Có thật sự muốn xóa xe " + xe.getBienSo() + " ?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    xeDataSource.xoaXe(xe.getBienSo());
                    dsXe.remove(selectedPosition);
                    adapterXe.notifyDataSetChanged();
                    xoaTrangForm();
                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("No", null)
                .create().show();
        return true;
    }
    return super.onContextItemSelected(item);
}
```

---

## 2. SOURCE CODE ĐẦY ĐỦ NGUYÊN FILE TỪNG LỚP JAVA

### 2.1 CarBooking_DatabaseHandler.java
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
```

---

### 2.2 Xe.java (Model)
Đường dẫn: `CarBookingManagement/app/src/main/java/com/rbys/carbookingmanagement/Xe.java`

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

    public Xe() {
        this.bienSo = "";
        this.namSX = 0;
        this.giaTri = 0.0;
        this.maHang = 0;
        this.tenHang = "";
        this.tenKhach = "";
        this.sdtKhach = "";
    }

    public Xe(String bienSo, int namSX, double giaTri, int maHang) {
        this.bienSo = bienSo;
        this.namSX = namSX;
        this.giaTri = giaTri;
        this.maHang = maHang;
        this.tenHang = "";
        this.tenKhach = "";
        this.sdtKhach = "";
    }

    public Xe(String bienSo, int namSX, double giaTri, int maHang, String tenHang) {
        this.bienSo = bienSo;
        this.namSX = namSX;
        this.giaTri = giaTri;
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.tenKhach = "";
        this.sdtKhach = "";
    }

    public Xe(String bienSo, int namSX, double giaTri, int maHang, String tenHang, String tenKhach, String sdtKhach) {
        this.bienSo = bienSo;
        this.namSX = namSX;
        this.giaTri = giaTri;
        this.maHang = maHang;
        this.tenHang = tenHang;
        this.tenKhach = tenKhach;
        this.sdtKhach = sdtKhach;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public int getNamSX() {
        return namSX;
    }

    public void setNamSX(int namSX) {
        this.namSX = namSX;
    }

    public double getGiaTri() {
        return giaTri;
    }

    public void setGiaTri(double giaTri) {
        this.giaTri = giaTri;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getTenKhach() {
        return tenKhach;
    }

    public void setTenKhach(String tenKhach) {
        this.tenKhach = tenKhach;
    }

    public String getSdtKhach() {
        return sdtKhach;
    }

    public void setSdtKhach(String sdtKhach) {
        this.sdtKhach = sdtKhach;
    }

    @Override
    public String toString() {
        String hang = (tenHang != null && !tenHang.isEmpty()) ? tenHang : "";
        return hang + " " + bienSo + " " + giaTri;
    }
}
```

---

### 2.3 HangXe.java (Model)
Đường dẫn: `CarBookingManagement/app/src/main/java/com/rbys/carbookingmanagement/HangXe.java`

```java
package com.rbys.carbookingmanagement;

import java.io.Serializable;

public class HangXe implements Serializable {
    private int maHang;
    private String tenHang;

    public HangXe() {
    }

    public HangXe(int maHang, String tenHang) {
        this.maHang = maHang;
        this.tenHang = tenHang;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    @Override
    public String toString() {
        return tenHang;
    }
}
```

---

### 2.4 MainActivity.java
Đường dẫn: `CarBookingManagement/app/src/main/java/com/rbys/carbookingmanagement/MainActivity.java`

```java
package com.rbys.carbookingmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_DAT_XE = 100;

    private EditText edtBienSo, edtNamSX, edtGiaTri;
    private Spinner spnHangXe;
    private Button btnThemXe, btnDatXe, btnDong;
    private ListView lvDanhSachXe;

    private XeDataSource xeDataSource;
    private HangXeDataSource hangXeDataSource;

    private ArrayList<Xe> dsXe;
    private ArrayList<HangXe> dsHangXe;
    private ArrayAdapter<Xe> adapterXe;
    private ArrayAdapter<HangXe> adapterHangXe;

    private int selectedPosition = -1;

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

        edtBienSo = findViewById(R.id.edtBienSo);
        edtNamSX = findViewById(R.id.edtNamSX);
        edtGiaTri = findViewById(R.id.edtGiaTri);
        spnHangXe = findViewById(R.id.spnHangXe);
        btnThemXe = findViewById(R.id.btnThemXe);
        btnDatXe = findViewById(R.id.btnDatXe);
        btnDong = findViewById(R.id.btnDong);
        lvDanhSachXe = findViewById(R.id.lvDanhSachXe);

        hangXeDataSource = new HangXeDataSource(this);
        hangXeDataSource.open();

        xeDataSource = new XeDataSource(this);
        xeDataSource.open();

        dsHangXe = hangXeDataSource.xuatDSHangXe();
        adapterHangXe = new ArrayAdapter<HangXe>(this,
                android.R.layout.simple_spinner_item, dsHangXe);
        adapterHangXe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnHangXe.setAdapter(adapterHangXe);

        dsXe = xeDataSource.xuatDSXe();
        adapterXe = new ArrayAdapter<Xe>(this,
                android.R.layout.simple_list_item_1, dsXe);
        lvDanhSachXe.setAdapter(adapterXe);

        registerForContextMenu(lvDanhSachXe);

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

    private void xuLyDong() {
        if (xeDataSource != null) {
            xeDataSource.close();
        }
        if (hangXeDataSource != null) {
            hangXeDataSource.close();
        }
        finish();
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu_xe, menu);
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_DAT_XE) {
            capNhatDanhSachXe();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xeDataSource != null) {
            xeDataSource.close();
        }
        if (hangXeDataSource != null) {
            hangXeDataSource.close();
        }
    }
}
```

---

### 2.5 DatXeActivity.java
Đường dẫn: `CarBookingManagement/app/src/main/java/com/rbys/carbookingmanagement/DatXeActivity.java`

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

### 2.6 XeDataSource.java
Đường dẫn: `CarBookingManagement/app/src/main/java/com/rbys/carbookingmanagement/XeDataSource.java`

```java
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
```

---

### 2.7 HangXeDataSource.java
Đường dẫn: `CarBookingManagement/app/src/main/java/com/rbys/carbookingmanagement/HangXeDataSource.java`

```java
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
```

---

## 3. FILE CẤU HÌNH BỔ TRỢ

### 3.1 context_menu_xe.xml (res/menu)
Đường dẫn: `CarBookingManagement/app/src/main/res/menu/context_menu_xe.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/mnuAdd"
        android:title="Add" />
    <item
        android:id="@+id/mnuEdit"
        android:title="Edit" />
    <item
        android:id="@+id/mnuDelete"
        android:title="Delete" />
</menu>
```

### 3.2 AndroidManifest.xml
Đường dẫn: `CarBookingManagement/app/src/main/AndroidManifest.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <application
        android:allowBackup="true"
        android:dataExtractionRules="@xml/data_extraction_rules"
        android:fullBackupContent="@xml/backup_rules"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.CarBookingManagement">
        <activity
            android:name=".MainActivity"
            android:exported="true"
            android:windowSoftInputMode="adjustResize">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".DatXeActivity"
            android:exported="false"
            android:windowSoftInputMode="adjustResize" />
    </application>

</manifest>
```
