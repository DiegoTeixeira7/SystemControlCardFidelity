package com.example.eng221.systemcontrolcardfidelity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class GeneratePointsClient extends AppCompatActivity {
    public static final int VOLTAR = 1;
    public static final int REGISTERPOINTS = 2;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem item = menu.add(0, VOLTAR, 1, "Voltar");
        menu.add(0, REGISTERPOINTS, 0, "Registrar Pontos");

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == REGISTERPOINTS) {
            Intent it = new Intent(this, RegisterPointsClient.class);
            startActivity(it);
            finish();
            return true;
        }

        else if(item.getItemId() == VOLTAR) {
            finish();
            return true;
        }

        return false;
    }

    public void generatePoints(View view) {
    }
}