package com.example.vagasapp.api;

import com.example.vagasapp.classes.Interesse;
import com.example.vagasapp.classes.Vaga;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("vagas/get-all")
    Call<List<Vaga>> getVagas();
    @POST("vagas/interesse")
    Call<Interesse> registrarInteresse(@Body Interesse interesse);
}