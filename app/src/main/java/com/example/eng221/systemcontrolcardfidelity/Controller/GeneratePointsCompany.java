package com.example.eng221.systemcontrolcardfidelity.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.eng221.systemcontrolcardfidelity.Model.ControladoraFachadaSingleton;
import com.example.eng221.systemcontrolcardfidelity.R;
import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GeneratePointsCompany extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final int VOLTAR = 1;
    public ArrayList<String> clientes = new ArrayList<>();
    public Map<Integer, Integer> map = new HashMap<Integer, Integer>();
    public ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_points_company);

        Spinner s = findViewById(R.id.spinnerClientes);
        s.setOnItemSelectedListener(this); //configura método de seleção
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clientes);
        s.setAdapter(adapter);
    }

    protected void onStart() {
        super.onStart();

        adapter.setNotifyOnChange(false);

        try {
            Cursor c = BancoDadosSingleton.getInstance().buscar("solicitacoesPontos", new String[]{"idSolicitacoesPontos","idCliente", "reais", "nomeC"}, "idEmpresa='"+1+"'", "");

            int i = 0;

            while(c.moveToNext()){
                int nomeC = c.getColumnIndex("nomeC");
                int idSolicitacoesPontos = c.getColumnIndex("idSolicitacoesPontos");
                int idCliente = c.getColumnIndex("idCliente");
                int reais = c.getColumnIndex("reais");

                clientes.add(c.getString(nomeC) + " - R$ " + c.getDouble(reais));
                map.put(i, c.getInt(idSolicitacoesPontos));
                i++;
            }

            // Habilitar novamente a notificacao
            adapter.setNotifyOnChange(true);
            // Notifica o Spinner de que houve mudanca no modelo
            adapter.notifyDataSetChanged();

            c.close();
        } catch (Exception e) {
            Toast.makeText(this, "Nenhuma solicitação", Toast.LENGTH_SHORT).show();
        }
    }

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

    public void onItemSelected(AdapterView parent, View v, int posicao, long id) {
        Integer idSolicitacao = map.get(posicao);
        Toast.makeText(this, "Item: " + clientes.get(posicao) + " id: " + idSolicitacao.toString() , Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView arg0) { }

    public void codes(View view) {
        //Toast.makeText(this,  ControladoraFachadaSingleton.getInstance(1).getEmpresa().exibirEmpresa(), Toast.LENGTH_LONG).show();

    }

}