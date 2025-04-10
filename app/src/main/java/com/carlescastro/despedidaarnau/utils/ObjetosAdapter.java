package com.carlescastro.despedidaarnau.utils;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlescastro.despedidaarnau.DetallesObjeto;
import com.carlescastro.despedidaarnau.R;
import com.carlescastro.despedidaarnau.modelo.TablaDTO;

import java.util.List;

public class ObjetosAdapter extends RecyclerView.Adapter<ObjetosAdapter.ViewHolder> {

    private List<TablaDTO> listadoDatosBBDD;

    public ObjetosAdapter(List<TablaDTO> listadoDatosBBDD) {
        this.listadoDatosBBDD = listadoDatosBBDD;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int posicion){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disenio_linea_datos, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TablaDTO todosLosDatosBBDD = listadoDatosBBDD.get(position);

        //Mostrar si es prueba o pregunta
        holder.textPreguntaOPrueba.setText(todosLosDatosBBDD.isPreguntaOPrueba() ? "Pregunta" : "Prueba");

        //Cambiar el color del fondo de pantalla segun el nivel
        int colorId = 0;
        switch (todosLosDatosBBDD.getNivel()){
            case 0:
                colorId = R.color.gray;
                break;
            case 1:
                colorId = R.color.green;
                break;
            case 2:
                colorId = R.color.yellow;
                break;
            case 3:
                colorId = R.color.red;
                break;
        }

        holder.itemView.setBackgroundResource(colorId);

        // Agregar listener para manejar el clic
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), DetallesObjeto.class);
            intent.putExtra("objetoId", todosLosDatosBBDD.getId()); // Enviar el ID único
            intent.putExtra("preguntaOPrueba", todosLosDatosBBDD.isPreguntaOPrueba());
            intent.putExtra("nivel", todosLosDatosBBDD.getNivel());
            intent.putExtra("descripcion", todosLosDatosBBDD.getDescripcion()); // Agregar descripción
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return  listadoDatosBBDD.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textPreguntaOPrueba;

        ViewHolder(View itemView) {
            super(itemView);
            textPreguntaOPrueba = itemView.findViewById(R.id.textPreguntaOPrueba);
        }
    }
}
