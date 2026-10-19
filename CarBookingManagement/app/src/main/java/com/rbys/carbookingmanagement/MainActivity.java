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