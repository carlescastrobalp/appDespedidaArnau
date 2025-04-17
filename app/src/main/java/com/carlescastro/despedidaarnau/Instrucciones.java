package com.carlescastro.despedidaarnau;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones); // Vincula con instructions.xml

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
        }, 8000);

        TextView instruccionesTextView = findViewById(R.id.pantalla2);
        String instrucciones = getString(R.string.instruccions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            instruccionesTextView.setText(Html.fromHtml(instrucciones, Html.FROM_HTML_MODE_LEGACY));
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
