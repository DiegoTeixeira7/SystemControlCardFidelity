package com.example.eng221.systemcontrolcardfidelity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class GeneratePointsClient extends AppCompatActivity {

    private static final String[] ESTADOS = new String[] { "Acre", "Alagoas", "Amapá","Amazonas",
            "Bahia", "Ceará", "Distrito Federal", "Goiás","Espírito Santo", "Maranhão", "Mato Grosso",
            "Mato Grosso do Sul","Minas Gerais", "Pará", "Paraíba", "Paraná", "Pernambuco", "Piauí",
            "Rio de Janeiro", "Rio Grandedo Norte", "Rio Grande do Sul","Rondônia", "Roraima",
            "São Paulo", "Santa Catarina", "Sergipe","Tocantins"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_points_client);

        //Cria um ArrayAdapter para exibir os estados
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, ESTADOS);
        AutoCompleteTextView estatos = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        estatos.setAdapter(adaptador);
    }

    public void generatePoints(View view) {
    }
}