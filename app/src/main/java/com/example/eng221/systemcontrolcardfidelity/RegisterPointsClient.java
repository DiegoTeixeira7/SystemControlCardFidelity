package com.example.eng221.systemcontrolcardfidelity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class RegisterPointsClient extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String[] planetas = new String[] { "Mercúrio", "Venus", "Terra", "Marte", "Júptier",
            "Saturno", "Urano","Netuno", "Plutão" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_points_client);

        Spinner combo = (Spinner) findViewById(R.id.spinnerPoints);
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

    public void generatePoints(View view) {
    }
}