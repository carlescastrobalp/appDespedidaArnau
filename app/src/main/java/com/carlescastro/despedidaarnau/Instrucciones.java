package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Instrucciones extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones); // Vincula con instructions.xml
        Button btnInsert = findViewById(R.id.btn_insert);

        TextView instruccionesTextView = findViewById(R.id.pantalla2);
        String instrucciones = getString(R.string.instruccions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            instruccionesTextView.setText(Html.fromHtml(instrucciones, Html.FROM_HTML_MODE_LEGACY));
        }

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentLayoutInsert = new Intent(Instrucciones.this, SeleccionPersonas.class);
                startActivity(intentLayoutInsert);
                finish();
            }
        });
    }
}
