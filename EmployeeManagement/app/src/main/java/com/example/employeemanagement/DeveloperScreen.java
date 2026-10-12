package com.example.employeemanagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DeveloperScreen extends Activity {
    EditText txtsnkn;
    Button btnBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themdeveloper);

        txtsnkn = (EditText) findViewById(R.id.txtSonamkn);
        btnBack = (Button) findViewById(R.id.btnSubmit);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                Bundle b = intent.getExtras();
                Bundle bundle = new Bundle();

                NhanVien nvien = (NhanVien) b.getSerializable("nv");
                Developer ltv = new Developer(nvien.getMnv(),nvien.getHoten(),Integer.parseInt(txtsnkn.getText().toString()));
                bundle.putSerializable("ltv",ltv);
                intent.putExtras(bundle);
                setResult(MainActivity.DEVELOPER_INFORESULT,intent);
                finish();
            }
        });

    }
}
