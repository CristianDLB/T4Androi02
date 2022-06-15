package com.example.t4androi02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.hardware.lights.LightsManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.t4androi02.Adaptador.peliculaAdapter;
import com.example.t4androi02.BaseDatos.AppDatos;
import com.example.t4androi02.BaseDatos.PelisDao;
import com.example.t4androi02.Entidad.Peliculas;
import com.example.t4androi02.Factor.RetroFactor;
import com.example.t4androi02.Servicio.peliService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    AppDatos db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatos.getDatabase(getApplicationContext());

        FloatingActionButton faB = findViewById(R.id.fab);
        faB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Registros.class);
                startActivity(intent);
            }
        });

        Retrofit retro = RetroFactor.Links();
        peliService servicio = retro.create(peliService.class);
        Call<List<Peliculas>> call =servicio.getPeli();

        call.enqueue(new Callback<List<Peliculas>>() {
            @Override
            public void onResponse(Call<List<Peliculas>> call, Response<List<Peliculas>> response) {
                if (!response.isSuccessful()){
                    Log.e("App_Peliculas","ERROR APP");
                }else {
                    Log.i("App_Peliculas","Me Conecteeee :)");
                    List<Peliculas> pe= response.body();

                    //guardoDatos(pe);

                    peliculaAdapter adapter = new peliculaAdapter(pe);

                    RecyclerView rv = findViewById(R.id.RvGeneral);
                    rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    rv.setHasFixedSize(true);
                    rv.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Peliculas>> call, Throwable t) {
                Log.e("App_Peliculas","No Hubo conectividad");
            }
        });
    }

    private void guardoDatos(List<Peliculas> pelis) {
        PelisDao dao = db.userDao();
        for (Peliculas pel : pelis) {
            dao.Crear(pel);
        }
    }
}