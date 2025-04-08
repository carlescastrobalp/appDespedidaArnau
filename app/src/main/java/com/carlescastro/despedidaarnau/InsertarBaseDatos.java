package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.carlescastro.despedidaarnau.modelo.TablaDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InsertarBaseDatos extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioBtnPregunta;
    private Button btnEnviar;
    private DatabaseReference dataBaseFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertar_base_datos);

        //radioBtnPregunta.requestFocus(R.id.rbPregunta);

        // Inicializar Firebase
        dataBaseFireStore = FirebaseDatabase.getInstance().getReference("objecte");

        radioGroup = findViewById(R.id.radioGroupTipo);
        btnEnviar = findViewById(R.id.btn_insert);

        // Botón de enviar datos a Firebase
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDatosFireBase();
                Intent intentLayoutInstruccion = new Intent(InsertarBaseDatos.this, Llistat.class);
                startActivity(intentLayoutInstruccion);
            }
        });
    }

    private void enviarDatosFireBase (){
        // Obtener selección de prueba o pregunta
        boolean seleccionTipo = radioGroup.getCheckedRadioButtonId() == R.id.rbPregunta;

        // Crear objeto de datos
        TablaDTO tablaDTO = new TablaDTO(seleccionTipo);

        dataBaseFireStore.push().setValue(tablaDTO);

    }
}
