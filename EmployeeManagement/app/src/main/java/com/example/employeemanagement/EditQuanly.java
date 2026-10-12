package com.example.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditQuanly extends Activity {
    EditText txtma, txtten, txtcap;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suaquanly);

        txtma = (EditText) findViewById(R.id.txtMNV);
        txtten = (EditText) findViewById(R.id.txthoten);
        txtcap = (EditText) findViewById(R.id.txtbac);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA1");
        QuanLy quanLy = (QuanLy) bundle.getSerializable("ql");
        txtma.setText(String.valueOf(quanLy.getMnv()));
        txtten.setText(quanLy.getHoten());
        txtcap.setText(String.valueOf(quanLy.getCapbac()));
        txtma.setEnabled(false);

        btnBack = (Button) findViewById(R.id.btnSua);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              quanLy.setHoten(txtten.getText().toString());
              quanLy.setCapbac(Integer.parseInt(txtcap.getText().toString()));
              setResult(MainActivity.QLT_EDITINFORESULT,intent);
              finish();
            }
        });

    }
}
