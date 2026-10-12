package com.example.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class NvKiemThuScreen extends Activity {
    RadioGroup loaikiemthu;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themnvtesting);

        loaikiemthu = (RadioGroup) findViewById(R.id.rdgrp);
        btnBack = (Button) findViewById(R.id.btnQuaylai);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selected_rd = loaikiemthu.getCheckedRadioButtonId();
                Intent intent = getIntent();
                Bundle b = intent.getExtras();
                Bundle bundle = new Bundle();

                NhanVien nvien = (NhanVien) b.getSerializable("nv");
                NhanVienKiemThu kiemthuemp;
                switch (selected_rd) {
                    case R.id.rdhethongtesting:
                        String s = "Kiểm thử hệ thống";
                        kiemthuemp = new NhanVienKiemThu(nvien.getMnv(), nvien.getHoten(), s);
                        bundle.putSerializable("nvkt",kiemthuemp);
                        intent.putExtras(bundle);
                        break;
                    case R.id.rdchucnangtesting:
                        String s1 = "Kiểm thử chức năng";
                        kiemthuemp = new NhanVienKiemThu(nvien.getMnv(), nvien.getHoten(), s1);
                        bundle.putSerializable("nvkt",kiemthuemp);
                        intent.putExtras(bundle);
                        break;
                }
                setResult(MainActivity.TESTING_INFORESULT,intent);
                finish();
            }
        });

    }
}
