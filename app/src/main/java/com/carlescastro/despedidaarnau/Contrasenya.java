package com.carlescastro.despedidaarnau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Contrasenya extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private EditText editTextContrasena;
    private Button btnContinuar;
    private String nombrePersona;
    private Map<String, String> passPersona, passModeTCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contrasenya);

        // Obtener el nombre de la persona desde el Intent
        nombrePersona = getIntent().getStringExtra("nombrePersona");

        // Inicializar mapa de contraseñas
        passPersona = new HashMap<>();
        passPersona.put("Arnau", "mongeta");
        passPersona.put("Ribas", "coliflor");
        passPersona.put("Eli", "esparrec");
        passPersona.put("Oriol", "espinac");
        passPersona.put("Marc", "pastanaga");
        passPersona.put("Ivan", "pedra");
        passPersona.put("Karen", "carxofa");
        passPersona.put("Roger", "api");
        passPersona.put("Laura", "carbasso");
        passPersona.put("Carles", "ratafia");

        // Inicializar mapa de contraseñas TCT
        passModeTCT = new HashMap<>();
        passModeTCT.put("Arnau", "mongetatct");
        passModeTCT.put("Ribas", "coliflortct");
        passModeTCT.put("Eli", "esparrectct");
        passModeTCT.put("Oriol", "espinactct");
        passModeTCT.put("Marc", "pastanagatct");
        passModeTCT.put("Ivan", "pedratct");
        passModeTCT.put("Karen", "carxofatct");
        passModeTCT.put("Roger", "apitct");
        passModeTCT.put("Laura", "carbassotct");
        passModeTCT.put("Carles", "ratafiatct");

        // Añadir las demás contraseñas según los nombres

        editTextContrasena = findViewById(R.id.editTextContrasena);
        btnContinuar = findViewById(R.id.btnAcceso);

        btnContinuar.setOnClickListener(v -> validarContrasena());

        // Reproducir audio al abrir la actividad
        mediaPlayer = MediaPlayer.create(this, R.raw.audio_contrasenya); // Usa el archivo en res/raw/audio.mp3
        mediaPlayer.start();

        // Liberar recursos al terminar el audio
        mediaPlayer.setOnCompletionListener(mp -> {
            mp.release();
            mediaPlayer = null;
        });
    }

    private void validarContrasena() {

        if (mediaPlayer != null) {
            mediaPlayer.stop();   // Detener la reproducción
            mediaPlayer.release(); // Liberar los recursos del MediaPlayer
            mediaPlayer = null;   // Evitar referencias nulas
        }

        String contrasenaIngresada = editTextContrasena.getText().toString().trim();
        if (TextUtils.isEmpty(contrasenaIngresada)) {
            Toast.makeText(this, "⚠️ Fica la contrasenya carallot", Toast.LENGTH_SHORT).show();
            return;
        }

        String contrasenaCorrecta = passPersona.get(nombrePersona);
        String contrasenaCorrectaTCT = passModeTCT.get(nombrePersona);
        boolean mode = false;

        if(contrasenaIngresada.equalsIgnoreCase(contrasenaCorrectaTCT)){
            mode = true;
        } else {
            mode = false;
        }

        if (contrasenaIngresada.equalsIgnoreCase(contrasenaCorrecta) || contrasenaIngresada.equalsIgnoreCase(contrasenaCorrectaTCT)) {
            if(contrasenaIngresada.equalsIgnoreCase("mongeta") && !mode){
                // Si es l'Arnau, vas directe a el llistat
                Intent intent = new Intent(Contrasenya.this, Listado.class);
                intent.putExtra("nombrePersona", nombrePersona); // Enviar el nombre de la persona
                // Guardar el nombre en SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("mode", mode);
                editor.apply();
                startActivity(intent);
                finish();
            } else if (!contrasenaIngresada.equalsIgnoreCase("mongeta") && contrasenaIngresada.equalsIgnoreCase(contrasenaCorrecta)){
                // Si no es l'Arnau va directe a la pantallade crear l'entrada
                Intent intent = new Intent(Contrasenya.this, InsertarBaseDatos.class);
                intent.putExtra("nombrePersona", nombrePersona); // Enviar el nombre de la persona
                // Guardar el nombre en SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("mode", mode);
                editor.apply();
                startActivity(intent);
                finish();
            } else if(contrasenaIngresada.equalsIgnoreCase(contrasenaCorrectaTCT)) {
                // Contraseña correcta, redirigir al layout de insertar datos
                Intent intent = new Intent(Contrasenya.this, pantallaTCT.class);
                intent.putExtra("nombrePersona", nombrePersona); // Enviar el nombre de la persona

                // Guardar el nombre en SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("mode", mode);
                editor.apply();

                startActivity(intent);
                finish();
            }
        } else {
            Toast.makeText(this, "⚠️ Muy mal, t'has equivocat pringui", Toast.LENGTH_SHORT).show();
        }
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
