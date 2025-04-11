package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionPersonas extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        Button botonArnau = findViewById(R.id.btnArnau);
        Button botonRibas = findViewById(R.id.btnRibas);
        Button botonEli = findViewById(R.id.btnEli);
        Button botonOriol = findViewById(R.id.btnOriol);
        Button botonMarc = findViewById(R.id.btnMarc);
        Button botonIvan = findViewById(R.id.btnIvan);
        Button botonKaren = findViewById(R.id.btnKaren);
        Button botonRoger = findViewById(R.id.btnRoger);
        Button botonLaura = findViewById(R.id.btnLaura);
        Button botonCarles = findViewById(R.id.btnCarles);

        botonArnau.setOnClickListener(v -> seleccionarPersona("Arnau"));
        botonRibas.setOnClickListener(v -> seleccionarPersona("Ribas"));
        botonEli.setOnClickListener(v -> seleccionarPersona("Eli"));
        botonOriol.setOnClickListener(v -> seleccionarPersona("Oriol"));
        botonMarc.setOnClickListener(v -> seleccionarPersona("Marc"));
        botonIvan.setOnClickListener(v -> seleccionarPersona("Ivan"));
        botonKaren.setOnClickListener(v -> seleccionarPersona("Karen"));
        botonRoger.setOnClickListener(v -> seleccionarPersona("Roger"));
        botonLaura.setOnClickListener(v -> seleccionarPersona("Laura"));
        botonCarles.setOnClickListener(v -> seleccionarPersona("Carles"));


    }

    private void seleccionarPersona(String nombrePersona) {

        // Guardar el nombre en SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombrePersona", nombrePersona);
        editor.apply();

        // Guardar el nombre de la persona y llevar al siguiente layout (contraseña)
        Intent intent = new Intent(SeleccionPersonas.this, Contrasenya.class);
        intent.putExtra("nombrePersona", nombrePersona); // Enviar el nombre seleccionado
        startActivity(intent);
        finish();
    }
}
