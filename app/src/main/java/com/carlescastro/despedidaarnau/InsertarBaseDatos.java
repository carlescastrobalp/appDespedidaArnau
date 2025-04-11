package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlescastro.despedidaarnau.modelo.TablaDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertarBaseDatos extends AppCompatActivity {

    private RadioGroup radioGroupTipo, radioGroupNivel;
    private EditText editTextDescripcion;
    private Button btnEnviar, btnVerListado;
    private DatabaseReference dataBaseFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_base_datos);

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);// Habilita persistencia en Firebase
        // Inicializar Firebase
        dataBaseFireStore = FirebaseDatabase.getInstance().getReference("objecte");

        radioGroupTipo = findViewById(R.id.radioGroupTipo);
        radioGroupNivel = findViewById(R.id.radioGroupNivel);
        editTextDescripcion = findViewById(R.id.textDescripcion);
        btnEnviar = findViewById(R.id.btn_insert);

        //Boton para layout del listado
        btnVerListado = findViewById(R.id.verListado);
        btnVerListado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InsertarBaseDatos.this, Listado.class);
                startActivity(intent);
            }
        });

        // Botón de enviar datos a Firebase
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorDesmarcado = radioGroupTipo.getCheckedRadioButtonId();
                String descripcion = editTextDescripcion.getText().toString().trim();

                // Validar entrada del usuario
                if (valorDesmarcado == -1) {
                    Toast.makeText(getApplicationContext(), "⚠️ Debes seleccionar Pregunta o Prueba", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(descripcion)) {
                    editTextDescripcion.setError("La descripción no puede estar vacía");
                } else {
                    enviarDatosFireBase();
                    Intent intentLayoutInstruccion = new Intent(InsertarBaseDatos.this, Listado.class);
                    startActivity(intentLayoutInstruccion);
                }
            }
        });
    }

    private void enviarDatosFireBase (){

        // Obtener selección de prueba o pregunta
        boolean seleccionTipo = radioGroupTipo.getCheckedRadioButtonId() == R.id.rbPregunta;

        //Obten el nivel de potencia
        int nivel;
        int idNivelSeleccionado = radioGroupNivel.getCheckedRadioButtonId();
        if(idNivelSeleccionado == R.id.rbNivel1) {
            nivel = 1;
        } else if (idNivelSeleccionado == R.id.rbNivel2) {
            nivel = 2;
        } else if (idNivelSeleccionado == R.id.rbNivel3) {
            nivel = 3;
        } else {
            nivel = 0;
        }

        String descripcion = editTextDescripcion.getText().toString().trim();
        String nombrePersona = getIntent().getStringExtra("nombrePersona"); // Obtener el nombre desde el Intent

        // Crear objeto de datos
        TablaDTO tablaDTO = new TablaDTO(null, seleccionTipo, nivel, descripcion, nombrePersona);
        tablaDTO.setEstado(0);
        tablaDTO.setNombre(nombrePersona); // Guardar el nombre

        dataBaseFireStore.push().setValue(tablaDTO)
                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Felicitats campio, has creat una nova petició", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(this, "Error al insertar les dades", Toast.LENGTH_SHORT).show());

        radioGroupTipo.clearCheck();
        radioGroupNivel.clearCheck();
        editTextDescripcion.setText("");
    }
}
