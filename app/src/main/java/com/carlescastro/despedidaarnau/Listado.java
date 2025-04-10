package com.carlescastro.despedidaarnau;

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
    private DatabaseReference dataBaseFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        recyclerView = findViewById(R.id.recycleViewListado);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        datosBBDD = new ArrayList<>();
        objetosAdapter = new ObjetosAdapter(datosBBDD);
        recyclerView.setAdapter(objetosAdapter);

        dataBaseFireStore = FirebaseDatabase.getInstance().getReference("objecte");
        mostrarDatosFireDatabase(0);

        btnNuevas = findViewById(R.id.btnNuevas);
        btnCompletadas = findViewById(R.id.btnCompletadas);
        btnRechazadas = findViewById(R.id.btnRechazadas);

        btnNuevas.setOnClickListener(v -> mostrarDatosFireDatabase(0)); // Cargar preguntas o pruebas nuevas
        btnCompletadas.setOnClickListener(v -> mostrarDatosFireDatabase(1)); // Cargar completadas
        btnRechazadas.setOnClickListener(v -> mostrarDatosFireDatabase(2)); // Cargar rechazadas
    }

    private void mostrarDatosFireDatabase(int filtroEstado) {

        dataBaseFireStore.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                datosBBDD.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {

                    Integer estado = objSnapshot.child("estado").getValue(Integer.class); // Obtener estado

                    if (estado != null && estado == filtroEstado) { // Solo incluir si el estado es "nuevo"
                        Boolean preguntaOPrueba = objSnapshot.child("preguntaOPrueba").getValue(Boolean.class);
                        Integer nivel = objSnapshot.child("nivel").getValue(Integer.class);
                        String descripcion = objSnapshot.child("descripcion").getValue(String.class);

                        if (preguntaOPrueba != null && nivel != null && descripcion != null) {
                            TablaDTO tablaDTO = new TablaDTO(objSnapshot.getKey(), preguntaOPrueba, nivel, descripcion); // Agregar ID único
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
        });
    }

}
