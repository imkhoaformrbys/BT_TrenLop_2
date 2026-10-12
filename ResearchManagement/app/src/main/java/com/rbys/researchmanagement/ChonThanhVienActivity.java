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
