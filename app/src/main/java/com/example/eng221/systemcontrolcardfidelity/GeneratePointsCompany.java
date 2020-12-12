package com.example.eng221.systemcontrolcardfidelity;

import androidx.appcompat.app.AppCompatActivity;

import android.location.LocationListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class GeneratePointsCompany extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final int VOLTAR = 1;

    private String[] planetas = new String[] { "Mercúrio", "Venus", "Terra", "Marte", "Júptier",
            "Saturno", "Urano","Netuno", "Plutão" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_points_company);

        Spinner combo = (Spinner) findViewById(R.id.spinnerClientes);
        combo.setOnItemSelectedListener(this); //configura método de seleção

        //configura adaptador
        ArrayAdapter<String> adaptadorSpinner =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, planetas);
        adaptadorSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item);

        //informa qual é o adaptador
        combo.setAdapter(adaptadorSpinner);
    }

    public void onItemSelected(AdapterView parent, View v, int posicao, long id) {
        Toast.makeText(this, "Item: " + planetas[posicao], Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView arg0) { }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.add(0, VOLTAR, 1, "Voltar");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == VOLTAR) {
            finish();
            return true;
        }

        return false;
    }

    public void codes(View view) {
    }
}