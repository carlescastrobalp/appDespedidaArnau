package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class pantallaTCT extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tct);

        String nombrePersona = getIntent().getStringExtra("nombrePersona");

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent intentLayoutInstruccion = new Intent(pantallaTCT.this, InsertarBaseDatos.class);
                intentLayoutInstruccion.putExtra("nombrePersona", nombrePersona); // Enviar el nombre de la persona
                startActivity(intentLayoutInstruccion);
                finish();
            }
        }, 3000);
    }
}
