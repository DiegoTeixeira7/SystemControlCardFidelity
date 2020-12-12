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