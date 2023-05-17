package com.example.projetofabrica;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class VisitaAdapter extends RecyclerView.Adapter<VisitaAdapter.VisitaViewHolder> {

    private List<Visit> visitas;
    private OnItemClickListener itemClickListener;

    public VisitaAdapter(List<Visit> visitas) {
        this.visitas = visitas;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public VisitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita, parent, false);
        return new VisitaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VisitaViewHolder holder, int position) {
        Visit visita = visitas.get(position);

        holder.textNomeVisita.setText(visita.getName());
        holder.textDataHoraVisita.setText(visita.getDateTime());

        holder.btnEditarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onEditClick(visita);
                }
            }
        });

        holder.btnCancelarVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onCancelClick(visita);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return visitas.size();
    }

    public void setVisitas(List<Visit> visitas) {
        this.visitas = visitas;
    }

    public static class VisitaViewHolder extends RecyclerView.ViewHolder {
        TextView textNomeVisita;
        TextView textDataHoraVisita;
        Button btnEditarVisita;
        Button btnCancelarVisita;

        public VisitaViewHolder(@NonNull View itemView) {
            super(itemView);
            textNomeVisita = itemView.findViewById(R.id.textNomeVisita);
            textDataHoraVisita = itemView.findViewById(R.id.textDataHoraVisita);
            btnEditarVisita = itemView.findViewById(R.id.btnEditarVisita);
            btnCancelarVisita = itemView.findViewById(R.id.btnCancelarVisita);
        }
    }

    public interface OnItemClickListener {
        void onEditClick(Visit visita);
        void onCancelClick(Visit visita);
    }
}

