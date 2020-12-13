package com.example.eng221.systemcontrolcardfidelity.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eng221.systemcontrolcardfidelity.Model.ControladoraFachadaSingleton;
import com.example.eng221.systemcontrolcardfidelity.R;
import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

public class GeneratePointsClient extends AppCompatActivity {
    public static final int VOLTAR = 1;
    public static final int REGISTERPOINTS = 2;

    ArrayList<String> empresas = new ArrayList<>();
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
        EditText priceEdt = findViewById(R.id.price);
        String price = priceEdt.getText().toString();

        if(price.equals("") || empr.equals("")){
            Toast.makeText(this, "Preencha todos os campos!" ,Toast.LENGTH_LONG).show();
        } else {
            Integer idEmpresa = map.get(empr);
            if (idEmpresa == null) {
                Toast.makeText(this, "Empresa digitada não cadastrada!" ,Toast.LENGTH_LONG).show();
            } else {
                double Price = parseDouble(price);

                if (Price <= 0) {
                    Toast.makeText(this, "Digite valor positivo maior que 0", Toast.LENGTH_LONG).show();
                } else {

                    ControladoraFachadaSingleton.getInstance().getCliente().solictarPontos(idEmpresa, Price);
                    priceEdt.setText(null);
                    autoC.setText("");

                    Toast.makeText(this, "Solicitação de pontos enviada para empresa " + empr + ". Valor informado R$" + Price, Toast.LENGTH_LONG).show();

                    //Toast.makeText(this,  ControladoraFachadaSingleton.getInstance().getCliente().exibirSolicitacoes(), Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}