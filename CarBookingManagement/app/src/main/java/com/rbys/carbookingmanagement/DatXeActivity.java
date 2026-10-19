package com.rbys.carbookingmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DatXeActivity extends AppCompatActivity {
    private EditText edtTenKhach;
    private Button btnThucHienDatXe, btnQuayLai;
    private XeDataSource xeDataSource;
    private Xe xeDuocChon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datxe);

        edtTenKhach = findViewById(R.id.edtTenKhach);
        btnThucHienDatXe = findViewById(R.id.btnThucHienDatXe);
        btnQuayLai = findViewById(R.id.btnQuayLai);

        xeDataSource = new XeDataSource(this);
        xeDataSource.open();

        xeDuocChon = (Xe) getIntent().getSerializableExtra("XE");
        if (xeDuocChon != null && xeDuocChon.getTenKhach() != null && !xeDuocChon.getTenKhach().isEmpty()) {
            edtTenKhach.setText(xeDuocChon.getTenKhach());
        }

        btnThucHienDatXe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKhach = edtTenKhach.getText().toString().trim();
                if (tenKhach.isEmpty()) {
                    Toast.makeText(DatXeActivity.this, "Vui lòng nhập tên khách hàng!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (xeDuocChon != null) {
                    xeDataSource.datXe(xeDuocChon.getBienSo(), tenKhach, "");
                    xeDuocChon.setTenKhach(tenKhach);

                    String msg = "Khách hàng " + tenKhach + " da dat xe "
                            + xeDuocChon.getTenHang() + " "
                            + xeDuocChon.getBienSo() + " "
                            + xeDuocChon.getGiaTri() + ". Saving into database";
                    Toast.makeText(DatXeActivity.this, msg, Toast.LENGTH_LONG).show();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("UPDATED_XE", xeDuocChon);
                    setResult(RESULT_OK, resultIntent);
                }
            }
        });

        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (xeDataSource != null) {
            xeDataSource.close();
        }
    }
}
