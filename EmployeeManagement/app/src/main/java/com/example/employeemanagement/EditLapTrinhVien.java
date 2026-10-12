package com.example.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditLapTrinhVien extends Activity {
    EditText txtma, txtten, txtkn;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sualtv);

        txtma = (EditText) findViewById(R.id.txtMNV);
        txtten = (EditText) findViewById(R.id.txthoten);
        txtkn = (EditText) findViewById(R.id.txtsnkn);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA2");
        Developer ltv = (Developer) bundle.getSerializable("ltv");
        txtma.setText(String.valueOf(ltv.getMnv()));
        txtten.setText(ltv.getHoten());
        txtkn.setText(String.valueOf(ltv.getSonam()));
        txtma.setEnabled(false);

        btnBack = (Button) findViewById(R.id.btnSua);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ltv.setHoten(txtten.getText().toString());
                ltv.setSonam(Integer.parseInt(txtkn.getText().toString()));
                setResult(MainActivity.DEVELOPER_EDITINFORESULT,intent);
                finish();
            }
        });
    }
}
