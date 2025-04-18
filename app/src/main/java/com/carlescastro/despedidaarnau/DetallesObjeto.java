package com.carlescastro.despedidaarnau;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class DetallesObjeto extends AppCompatActivity {
    private TextView textPreguntaOPrueba, textNivel, textDescripcion;
    private Button btnCompletar, btnRechazar;
    private ImageView pavo;
    private DatabaseReference databaseReference;
    private String objetoId; // ID único del objeto seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String nombrePersonaSharePreference = sharedPreferences.getString("nombrePersona", "Desconocido"); // Valor por defecto si es nulo
        boolean modeTCT = sharedPreferences.getBoolean("mode", false);

        // Vincular elementos del layout
        textPreguntaOPrueba = findViewById(R.id.textPreguntaOPrueba);
        textNivel = findViewById(R.id.textNivel);
        textDescripcion = findViewById(R.id.detalleDescripcion);
        btnCompletar = findViewById(R.id.btnCompletar);
        btnRechazar = findViewById(R.id.btnRechazar);
        pavo = findViewById(R.id.logo);

        pavo.setVisibility(INVISIBLE);

        if (modeTCT){
            btnCompletar.setEnabled(false);
            btnRechazar.setEnabled(false);
            btnCompletar.setVisibility(INVISIBLE);
            btnRechazar.setVisibility(INVISIBLE);
            pavo.setVisibility(VISIBLE);
        }

        // Referencia a Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("arnau");

        // Recibir datos del intent
        if (getIntent() != null) {
            objetoId = getIntent().getStringExtra("objetoId");
            if (objetoId == null) {
                Toast.makeText(this, "⚠️ Peto wey, no s'ha trobat el Objecte", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            //Que desaparezcan los botones si ya ha seleccionado 'Completado' o 'Rechazado'
            databaseReference.child(objetoId).child("estado").get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Integer estado = task.getResult().getValue(Integer.class);
                    if (estado != null && (estado == 1 || estado == 2 ) || (!"Arnau".equalsIgnoreCase(nombrePersonaSharePreference))) {
                        btnCompletar.setVisibility(INVISIBLE);
                        btnCompletar.setEnabled(false);
                        btnRechazar.setVisibility(INVISIBLE);
                        btnRechazar.setEnabled(false);
                    }
                } else {
                    Toast.makeText(DetallesObjeto.this, "Error al obtener estado", Toast.LENGTH_SHORT).show();
                }
            });

            textPreguntaOPrueba.setText(getIntent().getBooleanExtra("preguntaOPrueba", false) ? "Prueba" : "Pregunta");

            //Valor segun el nivel
            if(getIntent().getIntExtra("nivel", 0) == 0){
                textNivel.setText("Nivell: No s'ha seleccionat la intensitat");
            } else if(getIntent().getIntExtra("nivel", 0) == 1){
                textNivel.setText("Nivell: Suau, pots estar tranquil");
            } else if(getIntent().getIntExtra("nivel", 0) == 2){
                textNivel.setText("Nivell: Intensito, se fique la cosa interesant");
            } else if(getIntent().getIntExtra("nivel", 0) == 3){
                textNivel.setText("Nivell: Extrem, mort asegurada");
            }

            textDescripcion.setText(getIntent().getStringExtra("descripcion"));
        }

        // Botón Completar
        btnCompletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEstadoObjeto(1); // 1 = Completado
            }
        });

        // Botón Rechazar
        btnRechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEstadoObjeto(2); // 2 = Rechazado
            }
        });
    }

    private void actualizarEstadoObjeto(int nuevoEstado) {
        if (objetoId != null) {
            databaseReference.child(objetoId).child("estado").setValue(nuevoEstado)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(DetallesObjeto.this, "Resposta registrada", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DetallesObjeto.this, Listado.class);
                        startActivity(intent);
                        finish(); // Cerrar la actividad después de actualizar
                    })
                    .addOnFailureListener(e -> Toast.makeText(DetallesObjeto.this, "⚠️ no s'ha registrat la resposta", Toast.LENGTH_SHORT).show());
        }
    }
}
