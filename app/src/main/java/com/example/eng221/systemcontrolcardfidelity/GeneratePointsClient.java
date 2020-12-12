package com.example.eng221.systemcontrolcardfidelity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class GeneratePointsClient extends AppCompatActivity {
    public static final int VOLTAR = 1;
    public static final int REGISTERPOINTS = 2;

    ArrayList<String> empresas = new ArrayList<>();
    //Map map = new HashMap();
    Map<String, Integer> map = new HashMap<String, Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_points_client);

        //Cria um ArrayAdapter para exibir os estados
        ArrayAdapter<String> adaptador =
                new ArrayAdapter<String>(this,android.R.layout.select_dialog_item, empresas);
        AutoCompleteTextView estatos = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        estatos.setAdapter(adaptador);
    }

    protected void onStart() {
        super.onStart();
        Cursor c = BancoDadosSingleton.getInstance().buscar("empresa", new String[]{"nome","idEmpresa"}, "", "");

        while(c.moveToNext()){
            int nome = c.getColumnIndex("nome");
            int idEmpresa = c.getColumnIndex("idEmpresa");
            empresas.add(c.getString(nome));
            map.put(c.getString(nome), c.getInt(idEmpresa));
        }

        c.close();
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
        EditText autoC = findViewById(R.id.autoCompleteTextView);
        String empr = autoC.getText().toString();
        Integer id = map.get(empr);



        Toast.makeText(this, id.toString() ,Toast.LENGTH_LONG).show();
    }
}