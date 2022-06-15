package com.example.t4androi02;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.t4androi02.BaseDatos.AppDatos;
import com.example.t4androi02.BaseDatos.PelisDao;
import com.example.t4androi02.Entidad.Peliculas;
import com.example.t4androi02.Factor.RetroFactor;
import com.example.t4androi02.Servicio.peliService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Registros extends AppCompatActivity {

    AppDatos db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);



        Button BtnRegis = findViewById(R.id.btnRegistrando);
        EditText BtTitulo = findViewById(R.id.inTitulo);
        EditText BtDescripcion = findViewById(R.id.inSinopsis);
        EditText BtUrl = findViewById(R.id.inUrl);

        BtnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retro = RetroFactor.Links();
                peliService service = retro.create(peliService.class);
                Peliculas pelisTodo = new Peliculas();

                pelisTodo.titulo = String.valueOf(BtTitulo.getText());
                pelisTodo.sinopsis = String.valueOf(BtDescripcion.getText());
                pelisTodo.img = String.valueOf(BtUrl.getText());

                Call<Peliculas> call = service.posPeli(pelisTodo);
                call.enqueue(new Callback<Peliculas>() {
                    @Override
                    public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                        Log.i("App_Peliculas","Hubo conectividad");
                        Log.i("App_Peliculas",new Gson().toJson(response.body()));
                        Log.i("App_Peliculas","Pelicula  Registrada");
                        Toast.makeText(Registros.this,"Pelicula  Registrada",Toast.LENGTH_LONG).show();

                        db = AppDatos.getDatabase(getApplicationContext());
                        PelisDao dao = db.userDao();
                        List<Peliculas> peli = dao.getTodo();
                        Log.i("App_Peliculas",new Gson().toJson(peli));
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