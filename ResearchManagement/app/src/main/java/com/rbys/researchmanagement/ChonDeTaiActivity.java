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

public class ChonDeTaiActivity extends Activity {
    Spinner spnChonDeTai;
    Button btnChon;
    ThesisDataSource thesisDataSource;
    String selectedDeTai = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_detai);

        spnChonDeTai = (Spinner) findViewById(R.id.spnChonDeTai);
        btnChon = (Button) findViewById(R.id.btnChon);

        thesisDataSource = new ThesisDataSource(this);
        thesisDataSource.open();

        ArrayList<String> dsDeTai = thesisDataSource.layDSTenDeTai();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dsDeTai);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnChonDeTai.setAdapter(adapter);

        spnChonDeTai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDeTai = (String) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        btnChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDeTai.isEmpty()) {
                    Toast.makeText(ChonDeTaiActivity.this, "Vui long chon de tai", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(ChonDeTaiActivity.this, "De tai da dc chon: " + selectedDeTai, Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                resultIntent.putExtra("selectedDeTai", selectedDeTai);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thesisDataSource.close();
    }
}
