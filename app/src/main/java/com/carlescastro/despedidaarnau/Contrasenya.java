package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Contrasenya extends AppCompatActivity {
    private EditText editTextContrasena;
    private Button btnContinuar;
    private String nombrePersona;
    private Map<String, String> passPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasenya);

        // Obtener el nombre de la persona desde el Intent
        nombrePersona = getIntent().getStringExtra("nombrePersona");

        // Inicializar mapa de contraseñas
        passPersona = new HashMap<>();
        passPersona.put("Arnau", "arnau");
        passPersona.put("Ribas", "ribas");
        passPersona.put("Eli", "eli");
        passPersona.put("Oriol", "oriol");
        passPersona.put("Marc", "marc");
        passPersona.put("Ivan", "ivan");
        passPersona.put("Karen", "karen");
        passPersona.put("Roger", "roger");
        passPersona.put("Laura", "laura");
        passPersona.put("Carles", "carles");

        // Añadir las demás contraseñas según los nombres

        editTextContrasena = findViewById(R.id.editTextContrasena);
        btnContinuar = findViewById(R.id.btnAcceso);

        btnContinuar.setOnClickListener(v -> validarContrasena());
    }

    private void validarContrasena() {
        String contrasenaIngresada = editTextContrasena.getText().toString().trim();
        if (TextUtils.isEmpty(contrasenaIngresada)) {
            Toast.makeText(this, "⚠️ Fica la contrasenya carallot", Toast.LENGTH_SHORT).show();
            return;
        }

        String contrasenaCorrecta = passPersona.get(nombrePersona);
        if (contrasenaIngresada.equalsIgnoreCase(contrasenaCorrecta)) {
            if(nombrePersona.equalsIgnoreCase("arnau")){
                // Si es l'Arnau, vas directe a el llistat
                Intent intent = new Intent(Contrasenya.this, Listado.class);
                intent.putExtra("nombrePersona", nombrePersona); // Enviar el nombre de la persona
                startActivity(intent);
                finish();
            }else {
                // Contraseña correcta, redirigir al layout de insertar datos
                Intent intent = new Intent(Contrasenya.this, InsertarBaseDatos.class);
                intent.putExtra("nombrePersona", nombrePersona); // Enviar el nombre de la persona
                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, "⚠️ Muy mal, t'has equivocat", Toast.LENGTH_SHORT).show();
        }
    }

}
