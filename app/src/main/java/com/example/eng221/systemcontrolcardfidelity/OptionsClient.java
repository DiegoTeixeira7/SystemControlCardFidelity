package com.example.eng221.systemcontrolcardfidelity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class OptionsClient extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_client);
    }

    public void choiceActivity(View view) {
        String tag = view.getTag().toString();

        if (tag.equals("gerarPontos")) {
            Intent it = new Intent(getBaseContext(), GeneratePointsClient.class);
            startActivity(it);
        } else if (tag.equals("registerPoints")) {
            Intent it = new Intent(getBaseContext(), RegisterPointsClient.class);
            startActivity(it);
        }
    }
}