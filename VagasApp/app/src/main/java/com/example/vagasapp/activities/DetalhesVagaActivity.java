package com.example.vagasapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.vagasapp.R;
import com.example.vagasapp.classes.Candidato;
import com.example.vagasapp.classes.Interesse;
import com.example.vagasapp.classes.Vaga;
import com.example.vagasapp.api.ApiService;
import com.example.vagasapp.api.RetrofitConfig;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalhesVagaActivity extends AppCompatActivity {

    // Views para mostrar detalhes da vaga
    TextView tvCargoDetalhe, tvEmpresaDetalhe, tvRemuneracaoDetalhe, tvLocalDetalhe;
    TextView tvRegimeDetalhe, tvJornadaDetalhe, tvFormacaoDetalhe, tvRequisitosDetalhe, tvConhecimentosDetalhe;

    // Views do Formulário
    EditText etNome, etCpf, etEmail, etTelefone, etFormacao;
    Button btnEnviarInteresse;
    ProgressBar progressBarDetalhe;

    private Vaga vagaAtual;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_vaga);

        vagaAtual = (Vaga) getIntent().getSerializableExtra("VAGA_SELECIONADA");
        if (vagaAtual == null) {
            Toast.makeText(this, "Erro ao carregar vaga", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            apiService = new RetrofitConfig().getApiService();
            // Conecta os componentes do layout
            conectarViews();
            // Preenche os campos de detalhe com os dados da vaga
            preencherDetalhesVaga();
            // Configura o clique do botão
            btnEnviarInteresse.setOnClickListener(v -> registrarInteresse());
        }
    }

    private void conectarViews() {
        // Detalhes
        tvCargoDetalhe = findViewById(R.id.tvCargoDetalhe);
        tvEmpresaDetalhe = findViewById(R.id.tvEmpresaDetalhe);
        tvRemuneracaoDetalhe = findViewById(R.id.tvRemuneracaoDetalhe);
        tvLocalDetalhe = findViewById(R.id.tvLocalDetalhe);
        tvRegimeDetalhe = findViewById(R.id.tvRegimeDetalhe);
        tvJornadaDetalhe = findViewById(R.id.tvJornadaDetalhe);
        tvFormacaoDetalhe = findViewById(R.id.tvFormacaoDetalhe);
        tvRequisitosDetalhe = findViewById(R.id.tvRequisitosDetalhe);
        tvConhecimentosDetalhe = findViewById(R.id.tvConhecimentosDetalhe);
        // Formulário
        etNome = findViewById(R.id.etNome);
        etCpf = findViewById(R.id.etCpf);
        etEmail = findViewById(R.id.etEmail);
        etTelefone = findViewById(R.id.etTelefone);
        etFormacao = findViewById(R.id.etFormacao);
        btnEnviarInteresse = findViewById(R.id.btnEnviarInteresse);
        progressBarDetalhe = findViewById(R.id.progressBarDetalhe);
    }

    private void preencherDetalhesVaga() {
        tvCargoDetalhe.setText(vagaAtual.getCargo());
        tvEmpresaDetalhe.setText(vagaAtual.getEmpresa() != null ? vagaAtual.getEmpresa().getNomeFantasia() : "N/A");
        tvRemuneracaoDetalhe.setText(vagaAtual.getRemuneracao());
        tvLocalDetalhe.setText(vagaAtual.getCidade() + " - " + vagaAtual.getEstado());
        tvRegimeDetalhe.setText("Regime: " + vagaAtual.getRegime());
        tvJornadaDetalhe.setText("Jornada: " + vagaAtual.getJornadaTrabalho());
        tvFormacaoDetalhe.setText(vagaAtual.getFormacao());
        tvRequisitosDetalhe.setText(vagaAtual.getPreRequisitos());
        tvConhecimentosDetalhe.setText(vagaAtual.getConhecimentosRequeridos());
    }

    private void registrarInteresse() {
        String nome = etNome.getText().toString().trim();
        String cpf = etCpf.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String telefone = etTelefone.getText().toString().trim();
        String formacao = etFormacao.getText().toString().trim();

        if (nome.isEmpty() || cpf.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios!", Toast.LENGTH_SHORT).show();
        }
        else{
            //Mostra loading
            btnEnviarInteresse.setEnabled(false);
            progressBarDetalhe.setVisibility(View.VISIBLE);

            //Cria os objetos para enviar
            Candidato candidato = new Candidato(nome, cpf, email, telefone, formacao);
            Interesse interesse = new Interesse(vagaAtual, candidato);

            //Chama a API
            apiService.registrarInteresse(interesse).enqueue(new Callback<Interesse>() {
                @Override
                public void onResponse(Call<Interesse> call, Response<Interesse> response) {
                    btnEnviarInteresse.setEnabled(true);
                    progressBarDetalhe.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        Toast.makeText(DetalhesVagaActivity.this, "Interesse registrado com sucesso!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else {
                        Toast.makeText(DetalhesVagaActivity.this, "Erro ao registrar interesse.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Interesse> call, Throwable t) {
                    // Esconder loading
                    btnEnviarInteresse.setEnabled(true);
                    progressBarDetalhe.setVisibility(View.GONE);
                    Toast.makeText(DetalhesVagaActivity.this, "Falha na conexão: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}