package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Instrucciones extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrucciones); // Vincula con instructions.xml

        Button btnInsert = findViewById(R.id.btn_insert);

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
