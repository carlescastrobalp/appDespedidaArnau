package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.carlescastro.despedidaarnau.modelo.TablaDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertarBaseDatos extends AppCompatActivity {

    private RadioGroup radioGroupTipo, radioGroupNivel;
    private RadioButton radioBtnPregunta;
    private Button btnEnviar;
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
        btnEnviar = findViewById(R.id.btn_insert);

        // Botón de enviar datos a Firebase
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valorDesmarcado = radioGroupTipo.getCheckedRadioButtonId();
                if (valorDesmarcado == -1) {
                    Toast.makeText(getApplicationContext(), "⚠️ Tens que seleccion Pregunta o Proba", Toast.LENGTH_SHORT).show();
                    return; // Detiene la ejecución y evita que pase a la siguiente pantalla
                } else {
                    enviarDatosFireBase();
                    Intent intentLayoutInstruccion = new Intent(InsertarBaseDatos.this, Llistat.class);
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

        // Crear objeto de datos
        TablaDTO tablaDTO = new TablaDTO(seleccionTipo, nivel);

        dataBaseFireStore.push().setValue(tablaDTO);

    }
}
