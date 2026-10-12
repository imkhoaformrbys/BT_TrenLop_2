package com.example.employeemanagement;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final int QLT_INFOREQUEST = 100;
    static final int TESTING_INFOREQUEST = 101;
    static final int DEVELOPER_INFOREQUEST = 102;
    static final int QLT_EDITINFOREQUEST = 200;
    static final int TESTING_EDITINFOREQUEST = 201;
    static final int DEVELOPER_EDITINFOREQUEST = 202;
    static final int QLT_INFORESULT = 110;
    static final int TESTING_INFORESULT = 111;
    static final int DEVELOPER_INFORESULT = 112;

    static final int QLT_EDITINFORESULT = 210;
    static final int TESTING_EDITINFORESULT = 211;
    static final int DEVELOPER_EDITINFORESULT = 212;
    EditText txtmnv, txttennv;
    Spinner loainv;
    int nv, vitri;
    ArrayList<String> employeetypes = new ArrayList<String>();
    ArrayList<NhanVien> listnv;
    ArrayAdapter<NhanVien> nvadapter;
    ListView dsnv;
    Button btnThem, btnClose;
    NhanVienDataSource nhanVienDataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtmnv = (EditText) findViewById(R.id.txtmnv);
        txttennv = (EditText) findViewById(R.id.txttennv);
        loainv = (Spinner) findViewById(R.id.spinner);
        btnThem = (Button) findViewById(R.id.btnAdd);
        btnClose = (Button) findViewById(R.id.btnClose);
        employeetypes.add(new String("Quản lý trưởng"));
        employeetypes.add(new String("Nhân viên kiểm thử"));
        employeetypes.add(new String("Lập trình viên"));
      try {
            nhanVienDataSource = new NhanVienDataSource(this);
            nhanVienDataSource.open();
        }
        catch (Exception e) {}

      dsnv = (ListView) findViewById(R.id.lvNhanVien);
      listnv = nhanVienDataSource.xuatdsNhanVien();
        nvadapter = new ArrayAdapter<NhanVien>(this, android.R.layout.simple_list_item_1,listnv);
        dsnv.setAdapter(nvadapter);
        dsnv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = i;
                return false;
            }
        });
        registerForContextMenu(dsnv);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,employeetypes);
        loainv.setAdapter(adapter);
        loainv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                nv = i;
                //listnv = nhanVienDataSource.xuatdsNhanVienTheoLoaiNV(i+1);
                //nvadapter.notifyDataSetChanged();
                //dsnv.setAdapter(nvadapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    btnClose.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            nhanVienDataSource.close();
            System.exit(0);
        }
    });
    btnThem.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent;
            Bundle bundle = new Bundle();
            NhanVien nvien = new NhanVien(Integer.parseInt(txtmnv.getText().toString()),txttennv.getText().toString());
            bundle.putSerializable("nv",nvien);
            switch (nv) {
                case 0:
                    intent = new Intent(MainActivity.this, QuanLyScreen.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,MainActivity.QLT_INFOREQUEST);
                    break;
                case 1:
                    intent = new Intent(MainActivity.this,NvKiemThuScreen.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,MainActivity.TESTING_INFOREQUEST);
                    break;
                case 2:
                    intent = new Intent(MainActivity.this, DeveloperScreen.class);
                    intent.putExtras(bundle);
                    startActivityForResult(intent,MainActivity.DEVELOPER_INFOREQUEST);
                    break;
            }

        }
    });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mnuUpdate:
                if (listnv.get(vitri) instanceof QuanLy)
                 {
                    QuanLy qltruong = (QuanLy) listnv.get(vitri);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ql",qltruong);
                    Intent intent = new Intent(MainActivity.this,EditQuanly.class);
                    intent.putExtra("DATA1",bundle);
                    startActivityForResult(intent,MainActivity.QLT_EDITINFOREQUEST);
                 }
                else if (listnv.get(vitri) instanceof Developer)
                {
                    Developer developer = (Developer) listnv.get(vitri);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ltv",developer);
                    Intent intent = new Intent(MainActivity.this,EditLapTrinhVien.class);
                    intent.putExtra("DATA2",bundle);
                    startActivityForResult(intent,MainActivity.DEVELOPER_EDITINFOREQUEST);
                }
                else
                {
                    NhanVienKiemThu nhanVienKiemThu = (NhanVienKiemThu) listnv.get(vitri);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("nvkt",nhanVienKiemThu);
                    Intent intent = new Intent(MainActivity.this,EditKiemThuVien.class);
                    intent.putExtra("DATA3",bundle);
                    startActivityForResult(intent,MainActivity.TESTING_EDITINFOREQUEST);
                }
                break;
            case R.id.mnuDelete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Xác nhận");
                builder.setMessage("Có thật sự muốn xóa ?:");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (nhanVienDataSource.xoaNhanVien(listnv.get(vitri).getMnv()) != 0)
                            Toast.makeText(MainActivity.this,"Xoa thanh cong",Toast.LENGTH_LONG).show();
                        listnv.remove(vitri);
                        nvadapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.mnuxoasua,menu);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case MainActivity.QLT_INFOREQUEST:
                if (resultCode == MainActivity.QLT_INFORESULT)
                {
                    Bundle bundle = data.getExtras();
                    QuanLy ql = (QuanLy) bundle.getSerializable("qly");
                    nhanVienDataSource.themNVQuanly(ql.getMnv(),ql.getHoten(),ql.getCapbac());
                    listnv.add(ql);
                    nvadapter.notifyDataSetChanged();
                }
            case MainActivity.TESTING_INFOREQUEST:
                if (resultCode == MainActivity.TESTING_INFORESULT)
                {
                    Bundle bundle = data.getExtras();
                    NhanVienKiemThu kt = (NhanVienKiemThu) bundle.getSerializable("nvkt");
                    nhanVienDataSource.themNVKiemThu(kt.getMnv(),kt.getHoten(),kt.getLoaikt());
                    listnv.add(kt);
                    nvadapter.notifyDataSetChanged();
                }
            case MainActivity.DEVELOPER_INFOREQUEST:
                if (resultCode == MainActivity.DEVELOPER_INFORESULT)
                {
                    Bundle bundle = data.getExtras();
                    Developer ltv = (Developer) bundle.getSerializable("ltv");
                    nhanVienDataSource.themNVLaptrinh(ltv.getMnv(),ltv.getHoten(),ltv.getSonam());
                    listnv.add(ltv);
                    nvadapter.notifyDataSetChanged();
                }
            case MainActivity.QLT_EDITINFOREQUEST:
                if (resultCode == MainActivity.QLT_EDITINFORESULT)
                {
                    Bundle bundle = data.getBundleExtra("DATA1");
                    QuanLy ql = (QuanLy) bundle.getSerializable("ql");
                    if (nhanVienDataSource.suaNVQuanly(ql.getMnv(),ql.getHoten(),ql.getCapbac()) != 0)
                        Toast.makeText(MainActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    listnv.set(vitri,ql);
                    nvadapter.notifyDataSetChanged();
                }
            case MainActivity.TESTING_EDITINFOREQUEST:
                if (resultCode == MainActivity.TESTING_EDITINFORESULT)
                {
                    Bundle bundle = data.getBundleExtra("DATA3");
                    NhanVienKiemThu nvkt = (NhanVienKiemThu) bundle.getSerializable("nvkt");
                    if (nhanVienDataSource.suaNVTesting(nvkt.getMnv(),nvkt.getHoten(),nvkt.getLoaikt()) != 0)
                        Toast.makeText(MainActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    listnv.set(vitri,nvkt);
                    nvadapter.notifyDataSetChanged();
                }
            case MainActivity.DEVELOPER_EDITINFOREQUEST:
                if (resultCode == MainActivity.DEVELOPER_EDITINFORESULT)
                {
                    Bundle bundle = data.getBundleExtra("DATA2");
                    Developer laptrinhvien = (Developer) bundle.getSerializable("ltv");
                    if (nhanVienDataSource.suaNVLapTrinh(laptrinhvien.getMnv(),laptrinhvien.getHoten(),laptrinhvien.getSonam()) != 0)
                        Toast.makeText(MainActivity.this,"Cập nhật thành công",Toast.LENGTH_LONG).show();
                    listnv.set(vitri,laptrinhvien);
                    nvadapter.notifyDataSetChanged();
                }
        }
        txtmnv.setText("");
        txttennv.setText("");
        txtmnv.setFocusable(true);
    }
}