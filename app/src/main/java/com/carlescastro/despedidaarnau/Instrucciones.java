package com.carlescastro.despedidaarnau;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Instrucciones extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones); // Vincula con instructions.xml

        // Reproducir audio al abrir la actividad
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_instruccions); // Usa el archivo en res/raw/audio.mp3
        mediaPlayer.start();
        // Liberar recursos al terminar el audio
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });

        ImageView imageView = findViewById(R.id.logo);
        Button btnInsert = findViewById(R.id.btn_insert);
        btnInsert.setEnabled(false);
        btnInsert.setVisibility(INVISIBLE);

        imageView.setVisibility(INVISIBLE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                btnInsert.setEnabled(true);
                btnInsert.setVisibility(VISIBLE);
            }
        }, 6000);

        TextView instruccionesTextView = findViewById(R.id.pantalla2);
        String instrucciones = getString(R.string.instruccions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            instruccionesTextView.setText(Html.fromHtml(instrucciones, Html.FROM_HTML_MODE_LEGACY));
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null) {
                    mediaPlayer.stop();   // Detener la reproducción
                    mediaPlayer.release(); // Liberar los recursos del MediaPlayer
                    mediaPlayer = null;   // Evitar referencias nulas
                }

                instruccionesTextView.setVisibility(INVISIBLE);
                imageView.setVisibility(VISIBLE);
                btnInsert.setVisibility(INVISIBLE);
                btnInsert.setEnabled(false);

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent intentLayoutInsert = new Intent(Instrucciones.this, SeleccionPersonas.class);
                        startActivity(intentLayoutInsert);
                        finish();
                    }
                }, 900);
            }
        });
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
