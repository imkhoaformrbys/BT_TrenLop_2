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