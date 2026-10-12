package com.example.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class EditKiemThuVien extends Activity {
    EditText txtma, txtten;
    RadioGroup gpchon;
    RadioButton rdkiemthuchucnang, rdkiemthuhethong;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suanvkiemthu);

        txtma = (EditText) findViewById(R.id.txtMNV);
        txtten = (EditText) findViewById(R.id.txthoten);
        gpchon = (RadioGroup) findViewById(R.id.gplkt);
        rdkiemthuchucnang = (RadioButton) findViewById(R.id.rdchucnang);
        rdkiemthuhethong = (RadioButton) findViewById(R.id.rdhethong);

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DATA3");
        final NhanVienKiemThu kt = (NhanVienKiemThu) bundle.getSerializable("nvkt");
        txtma.setText(String.valueOf(kt.getMnv()));
        txtten.setText(kt.getHoten());
        if (kt.getLoaikt().equals("kiểm thử chức năng"))
            rdkiemthuchucnang.setChecked(true);
        else
            rdkiemthuhethong.setChecked(true);
        txtma.setEnabled(false);

        btnBack = (Button) findViewById(R.id.btnSua);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kt.setHoten(txtten.getText().toString());
                switch (gpchon.getCheckedRadioButtonId())
                {
                    case R.id.rdchucnang:
                        kt.setLoaikt("Kiểm thử chức năng");
                    case R.id.rdhethong:
                        kt.setLoaikt("Kiểm thử hệ thống");
                }
                setResult(MainActivity.TESTING_EDITINFORESULT,intent);
                finish();
            }
        });

    }
}
