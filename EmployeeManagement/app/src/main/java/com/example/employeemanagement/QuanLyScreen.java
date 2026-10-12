package com.example.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class QuanLyScreen extends Activity {
    EditText txtcapbac;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themquanly);

        txtcapbac = (EditText) findViewById(R.id.txtbac);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle b = intent.getExtras();
                Bundle bundle = new Bundle();

                NhanVien nvien = (NhanVien) b.getSerializable("nv");
                QuanLy quanLy = new QuanLy(nvien.getMnv(),nvien.getHoten(),Integer.parseInt(txtcapbac.getText().toString()));
                bundle.putSerializable("qly",quanLy);
                intent.putExtras(bundle);
                setResult(MainActivity.QLT_INFORESULT,intent);
                finish();
            }
        });

    }
}
