package com.example.eng221.systemcontrolcardfidelity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eng221.systemcontrolcardfidelity.Util.BancoDadosSingleton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void onStart() {
        super.onStart();
        Cursor c = BancoDadosSingleton.getInstance().buscar("empresa", new String[]{"nome"}, "", "");
        String aux = "";

        while(c.moveToNext()){
            int nome = c.getColumnIndex("nome");

            aux += "Empresa:" + c.getString(nome) + "\n\n";
        }

        Toast.makeText(this,aux, Toast.LENGTH_LONG).show();

        c.close();
    }

    public void choiceActivity(View view) {
        String tag = view.getTag().toString();

        if (tag.equals("cliente")) {
            Intent it = new Intent(getBaseContext(), GeneratePointsClient.class);
            startActivity(it);
        } else if (tag.equals("empresa")) {
            Intent it = new Intent(getBaseContext(), GeneratePointsCompany.class);
            startActivity(it);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BancoDadosSingleton.getInstance().fechar();
    }
}