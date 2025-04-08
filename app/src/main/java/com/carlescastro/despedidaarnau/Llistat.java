package com.carlescastro.despedidaarnau;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.carlescastro.despedidaarnau.utils.ObjetosAdapter;
import com.google.firebase.Firebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Llistat extends AppCompatActivity {

    private List<Boolean> booleanListPreguntaOPrueba;
    private RecyclerView recyclerView;
    private ObjetosAdapter objetosAdapter;
    private DatabaseReference dataBaseFireStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);

        recyclerView = findViewById(R.id.recycleViewListado);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        booleanListPreguntaOPrueba = new ArrayList<>();
        objetosAdapter = new ObjetosAdapter(booleanListPreguntaOPrueba);
        recyclerView.setAdapter(objetosAdapter);

        dataBaseFireStore = FirebaseDatabase.getInstance().getReference("objecte");
        mostrarDatosFireDatabase();

    }

    private void mostrarDatosFireDatabase() {

        dataBaseFireStore.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                booleanListPreguntaOPrueba.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()) {
                    Boolean preguntaOPrueba = objSnapshot.child("preguntaOPrueba").getValue(Boolean.class);
                    if (preguntaOPrueba != null) {
                        booleanListPreguntaOPrueba.add(preguntaOPrueba);
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
