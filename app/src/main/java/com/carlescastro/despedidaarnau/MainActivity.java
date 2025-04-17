package com.carlescastro.despedidaarnau;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnMain = findViewById(R.id.btn_instrucciones);
        ImageView miImagen = findViewById(R.id.imatgePrincipal);

        btnMain.setEnabled(false);
        btnMain.setVisibility(INVISIBLE);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                btnMain.setEnabled(true);
                btnMain.setVisibility(VISIBLE);
            }
        }, 3000);

        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnMain.setEnabled(false);
                btnMain.setVisibility(INVISIBLE);

                // Cambia el fondo del layout principal
                miImagen.setImageResource(R.drawable.arnau1); // tu imagen en drawable

                new Handler().postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        Intent intentLayoutInstruccion = new Intent(MainActivity.this, Instrucciones.class);
                        startActivity(intentLayoutInstruccion);
                        finish();
                    }
                }, 900);
            }
        });
    }
}