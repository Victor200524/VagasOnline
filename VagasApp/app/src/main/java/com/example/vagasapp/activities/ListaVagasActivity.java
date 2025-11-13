package com.example.vagasapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vagasapp.R;
import com.example.vagasapp.classes.VagasAdapter;
import com.example.vagasapp.classes.Vaga;
import com.example.vagasapp.api.ApiService;
import com.example.vagasapp.api.RetrofitConfig;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaVagasActivity extends AppCompatActivity {

    RecyclerView recyclerViewVagas;
    ProgressBar progressBar;
    VagasAdapter vagasAdapter;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_vagas);

        recyclerViewVagas = findViewById(R.id.recyclerViewVagas);
        progressBar = findViewById(R.id.progressBar);

        recyclerViewVagas.setLayoutManager(new LinearLayoutManager(this));
        vagasAdapter = new VagasAdapter(new ArrayList<>()); // Começa com lista vazia
        recyclerViewVagas.setAdapter(vagasAdapter);

        apiService = new RetrofitConfig().getApiService();

        buscarVagas();
    }

    private void buscarVagas() {
        progressBar.setVisibility(View.VISIBLE); // Mostrar loading
        recyclerViewVagas.setVisibility(View.GONE);

        apiService.getVagas().enqueue(new Callback<List<Vaga>>() {
            @Override
            public void onResponse(Call<List<Vaga>> call, Response<List<Vaga>> response) {
                progressBar.setVisibility(View.GONE); // Esconder loading
                recyclerViewVagas.setVisibility(View.VISIBLE);

                if (response.isSuccessful() && response.body() != null) {
                    // Atualiza o adapter com as vagas recebidas
                    vagasAdapter.setVagas(response.body());
                }
                else {
                    Toast.makeText(ListaVagasActivity.this, "Erro ao buscar vagas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Vaga>> call, Throwable t) {
                progressBar.setVisibility(View.GONE); // Esconder loading
                recyclerViewVagas.setVisibility(View.VISIBLE);
                Toast.makeText(ListaVagasActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}