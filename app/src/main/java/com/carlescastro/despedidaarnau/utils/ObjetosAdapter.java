package com.carlescastro.despedidaarnau.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.carlescastro.despedidaarnau.R;

import java.util.List;

public class ObjetosAdapter extends RecyclerView.Adapter<ObjetosAdapter.ViewHolder> {

    private List<Boolean> listadoPreguntaOPrueba;

    public ObjetosAdapter(List<Boolean> listadoPreguntaOPrueba) {
        this.listadoPreguntaOPrueba = listadoPreguntaOPrueba;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int posicion){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_objeto, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Boolean preguntaOPrueba = listadoPreguntaOPrueba.get(position);
        holder.textPreguntaOPrueba.setText(preguntaOPrueba ? "Pregunta" : "Prueba");
    }

    @Override
    public int getItemCount() {
        return  listadoPreguntaOPrueba.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textPreguntaOPrueba;

        ViewHolder(View itemView) {
            super(itemView);
            textPreguntaOPrueba = itemView.findViewById(R.id.textPreguntaOPrueba);
        }
    }
}
