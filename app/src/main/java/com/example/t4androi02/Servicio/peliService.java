package com.example.t4androi02.Servicio;

import com.example.t4androi02.Entidad.Peliculas;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface peliService {

    @GET("peliculas")
    Call<List<Peliculas>> getPeli();

    @GET("peliculas/{id}")
    Call<Peliculas> idEtidPeli(@Path("id")int id);

    @POST("peliculas")
    Call<Peliculas> posPeli(@Body Peliculas pelicula);

    @DELETE("peliculas/{id}")
    Call<Peliculas> idEliminar(@Path("id")int id);


}
