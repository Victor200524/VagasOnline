package com.example.vagasapp.classes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vagasapp.activities.DetalhesVagaActivity;
import com.example.vagasapp.R;
import java.util.List;

public class VagasAdapter extends RecyclerView.Adapter<VagasAdapter.VagaViewHolder> {

    private List<Vaga> vagas;
    private Context context;

    public VagasAdapter(List<Vaga> vagas) {
        this.vagas = vagas;
    }

    public void setVagas(List<Vaga> vagas) {
        this.vagas = vagas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VagaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.list_item_vaga, parent, false);
        return new VagaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VagaViewHolder holder, int position) {
        Vaga vaga = vagas.get(position);

        holder.tvCargo.setText(vaga.getCargo());
        holder.tvRemuneracao.setText(vaga.getRemuneracao());
        holder.tvLocal.setText(vaga.getCidade() + " - " + vaga.getEstado());

        if (vaga.getEmpresa() != null) {
            holder.tvEmpresa.setText(vaga.getEmpresa().getNomeFantasia());
        } else {
            holder.tvEmpresa.setText("Empresa não informada");
        }

        holder.itemView.setOnClickListener(v -> {
            // Abre a tela de detalhes
            Intent intent = new Intent(context, DetalhesVagaActivity.class);

            // Colocar o objeto Vaga inteiro dentro da Intent
            intent.putExtra("VAGA_SELECIONADA", vaga);

            // Iniciar a nova activity
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return vagas.size();
    }

    public static class VagaViewHolder extends RecyclerView.ViewHolder {
        TextView tvCargo, tvEmpresa, tvLocal, tvRemuneracao;

        public VagaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCargo = itemView.findViewById(R.id.tvCargo);
            tvEmpresa = itemView.findViewById(R.id.tvEmpresa);
            tvLocal = itemView.findViewById(R.id.tvLocal);
            tvRemuneracao = itemView.findViewById(R.id.tvRemuneracao);
        }
    }
}