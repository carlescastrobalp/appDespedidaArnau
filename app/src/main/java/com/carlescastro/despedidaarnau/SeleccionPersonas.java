package com.carlescastro.despedidaarnau;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SeleccionPersonas extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        ImageView imgGente = findViewById(R.id.imatgeGent);
        imgGente.setVisibility(INVISIBLE);

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

        // Reproducir audio al abrir la actividad
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_nom); // Usa el archivo en res/raw/audio.mp3
        mediaPlayer.start();

        // Liberar recursos al terminar el audio
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    }

    private void seleccionarPersona(String nombrePersona) {

        // Guardar el nombre en SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("nombrePersona", nombrePersona);
        editor.apply();

        //Selector de la imagen de la persona
        GridLayout gridLayout = findViewById(R.id.gridlayout);
        gridLayout.setVisibility(INVISIBLE);
        gridLayout.setEnabled(false);
        ImageView imgGente = findViewById(R.id.imatgeGent);
        imgGente.setVisibility(VISIBLE);
        ImageView saps_com_et_dius = findViewById(R.id.saps_com_et_dius);
        saps_com_et_dius.setVisibility(INVISIBLE);


        if (nombrePersona.equals("Arnau")){
            imgGente.setImageResource(R.drawable.arnau2);
        } else if (nombrePersona.equals("Ribas")) {
            imgGente.setImageResource(R.drawable.ribas_selector);
        } else if (nombrePersona.equals("Eli")) {
            imgGente.setImageResource(R.drawable.eli_selector);
        } else if (nombrePersona.equals("Oriol")) {
            imgGente.setImageResource(R.drawable.oriol_selector);
        } else if (nombrePersona.equals("Marc")) {
            imgGente.setImageResource(R.drawable.marc_selector);
        } else if (nombrePersona.equals("Ivan")) {
            imgGente.setImageResource(R.drawable.ivan_selector2);
        } else if (nombrePersona.equals("Karen")) {
            imgGente.setImageResource(R.drawable.karen_selector1);
        } else if (nombrePersona.equals("Roger")) {
            imgGente.setImageResource(R.drawable.roger_selector);
        } else if (nombrePersona.equals("Laura")) {
            imgGente.setImageResource(R.drawable.ivan_selector1);
        } else if (nombrePersona.equals("Carles")) {
            imgGente.setImageResource(R.drawable.carles_selector);
        }

        if (mediaPlayer != null) {
            mediaPlayer.stop();   // Detener la reproducción
            mediaPlayer.release(); // Liberar los recursos del MediaPlayer
            mediaPlayer = null;   // Evitar referencias nulas
        }

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                // Guardar el nombre de la persona y llevar al siguiente layout (contraseña)
                Intent intent = new Intent(SeleccionPersonas.this, Contrasenya.class);
                intent.putExtra("nombrePersona", nombrePersona); // Enviar el nombre seleccionado
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release(); // Liberar recursos del MediaPlayer
            mediaPlayer = null;    // Evitar referencias inválidas
        }
    }
}
