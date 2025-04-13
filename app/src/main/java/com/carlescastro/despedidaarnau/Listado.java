package com.carlescastro.despedidaarnau;

import static android.view.View.INVISIBLE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlescastro.despedidaarnau.modelo.TablaDTO;
import com.carlescastro.despedidaarnau.utils.ObjetosAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Listado extends AppCompatActivity {

    private List<TablaDTO> datosBBDD;
    private RecyclerView recyclerView;
    private Button btnNuevas, btnCompletadas, btnRechazadas;
    private ObjetosAdapter objetosAdapter;
    private DatabaseReference dataBaseFireStore, dataBaseFireStoreTCT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        recyclerView = findViewById(R.id.recycleViewListado);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        datosBBDD = new ArrayList<>();
        objetosAdapter = new ObjetosAdapter(datosBBDD);
        recyclerView.setAdapter(objetosAdapter);

        //Obtenemos el nombre de la persona para luego poder mostrar todas las entradas que le pertenecen
        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String nombreActual = sharedPreferences.getString("nombrePersona", "Desconocido"); // Valor por defecto si es nulo
        boolean modeTCT = sharedPreferences.getBoolean("mode", false);

        dataBaseFireStore = FirebaseDatabase.getInstance().getReference("arnau");
        dataBaseFireStoreTCT = FirebaseDatabase.getInstance().getReference("todos-contra-todos");
        mostrarDatosFireDatabase(0, nombreActual, modeTCT);

        btnNuevas = findViewById(R.id.btnNuevas);
        btnCompletadas = findViewById(R.id.btnCompletadas);
        btnRechazadas = findViewById(R.id.btnRechazadas);

        btnNuevas.setOnClickListener(v -> mostrarDatosFireDatabase(0, nombreActual, modeTCT)); // Cargar preguntas o pruebas nuevas
        btnCompletadas.setOnClickListener(v -> mostrarDatosFireDatabase(1, nombreActual, modeTCT)); // Cargar completadas
        btnRechazadas.setOnClickListener(v -> mostrarDatosFireDatabase(2, nombreActual, modeTCT)); // Cargar rechazadas
    }

    private void mostrarDatosFireDatabase(int filtroEstado, String nombreActual, boolean modeTCT) {

        ///////// Parte normal
        if(!modeTCT){
        dataBaseFireStore.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datosBBDD.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {

                    Integer estado = objSnapshot.child("estado").getValue(Integer.class);// Obtener estado

                    //Obtenemos el modeTCT para luego poder mostrar todas las entradas que le pertenecen
                    SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    boolean modeTCT = sharedPreferences.getBoolean("mode", false); // Valor por defecto si es false

                    if (estado != null && estado == filtroEstado && !modeTCT) { // Solo incluir si el estado es "nuevo"
                        Boolean preguntaOPrueba = objSnapshot.child("preguntaOPrueba").getValue(Boolean.class);
                        Integer nivel = objSnapshot.child("nivel").getValue(Integer.class);
                        String descripcion = objSnapshot.child("descripcion").getValue(String.class); // Cargar el nombre desde Firebase

                        if (preguntaOPrueba != null && nivel != null && descripcion != null) {
                            TablaDTO tablaDTO = new TablaDTO(objSnapshot.getKey(), preguntaOPrueba, nivel, descripcion, null, null, false); // Agregar ID único
                            datosBBDD.add(tablaDTO);
                        }
                    }
                }
                objetosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error al leer datos", error.toException());
            }
        });}


        /////////// Parte TCT
        if(modeTCT){
        dataBaseFireStoreTCT.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                btnNuevas.setEnabled(false);
                btnRechazadas.setEnabled(false);
                btnCompletadas.setEnabled(false);
                btnNuevas.setVisibility(INVISIBLE);
                btnRechazadas.setVisibility(INVISIBLE);
                btnCompletadas.setVisibility(INVISIBLE);

                datosBBDD.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {

                    Integer estado = objSnapshot.child("estado").getValue(Integer.class);// Obtener estado
                    String nombreVictima = objSnapshot.child("nombreVictima").getValue(String.class);

                    //Obtenemos el modeTCT para luego poder mostrar todas las entradas que le pertenecen
                    SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);
                    boolean modeTCT = sharedPreferences.getBoolean("mode", false); // Valor por defecto si es false

                    if (estado != null && estado == filtroEstado && modeTCT && nombreActual.equalsIgnoreCase(nombreVictima)) {
                        Boolean preguntaOPrueba = objSnapshot.child("preguntaOPrueba").getValue(Boolean.class);
                        Integer nivel = objSnapshot.child("nivel").getValue(Integer.class);
                        String descripcion = objSnapshot.child("descripcion").getValue(String.class); // Cargar el nombre desde Firebase

                        if (preguntaOPrueba != null && nivel != null && descripcion != null) {
                            TablaDTO tablaDTO = new TablaDTO(objSnapshot.getKey(), preguntaOPrueba, nivel, descripcion, null, null, false); // Agregar ID único
                            datosBBDD.add(tablaDTO);
                        }
                    }
                }
                objetosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error al leer datos", error.toException());
            }
        });}
    }

}
