package com.example.t4androi02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t4androi02.Entidad.Peliculas;
import com.example.t4androi02.Factor.RetroFactor;
import com.example.t4androi02.Servicio.peliService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Detalles extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);

        String animeJson2 = getIntent().getStringExtra("PELICULAS");
        Peliculas pelicula = new Gson().fromJson(animeJson2,Peliculas.class);

        ImageView tvAvatar = findViewById(R.id.deImgr);
        TextView tvTitulo = findViewById(R.id.deTitulo);
        TextView tvSinopsis = findViewById(R.id.deSinopsis);

        Picasso.get().load(pelicula.img).into(tvAvatar);
        tvTitulo.setText(pelicula.titulo);
        tvSinopsis.setText(pelicula.sinopsis);

        Button btnEdito = findViewById(R.id.btnEditar);

        Button btnElimino = findViewById(R.id.btnEliminar);
        btnElimino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retro = RetroFactor.Links();
                peliService servicio = retro.create(peliService.class);

                Call<Peliculas> call = servicio.idEliminar(pelicula.id);
                call.enqueue(new Callback<Peliculas>() {
                    @Override
                    public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                        if (!response.isSuccessful()){
                            Log.e("App_Peliculas","No Se pudo Eliminar");
                        }else{
                            Log.e("App_Peliculas","Se Elimino Pelicula: "+ pelicula.id);

                            Toast.makeText(Detalles.this,"Pelicula  Eliminada",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Peliculas> call, Throwable t) {
                        Log.e("App_Peliculas","No Hubo conectividad");
                    }
                });

            }
        });

    }
}