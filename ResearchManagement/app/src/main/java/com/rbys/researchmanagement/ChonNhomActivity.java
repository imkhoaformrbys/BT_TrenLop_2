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
