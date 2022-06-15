package com.example.t4androi02.Adaptador;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.t4androi02.Detalles;
import com.example.t4androi02.Entidad.Peliculas;
import com.example.t4androi02.Factor.RetroFactor;
import com.example.t4androi02.R;
import com.example.t4androi02.Servicio.peliService;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class peliculaAdapter extends RecyclerView.Adapter<peliculaAdapter.peliculaHolder> {

    List<Peliculas> peliculas;

    public peliculaAdapter(List<Peliculas> peliculas) {
        this.peliculas = peliculas;
    }

    @NonNull
    @Override
    public peliculaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.mostrar_pelis,parent,false);
        peliculaHolder holder = new peliculaHolder(vista);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull peliculaHolder holder, int position) {
        View view = holder.itemView;
        Peliculas peli = peliculas.get(position);
        TextView TvTitulo = view.findViewById(R.id.RVtitulo);
        TextView TvSinopsis = view.findViewById(R.id.RVsinopsis);
        ImageView TvImg = view.findViewById(R.id.RVimg);

        TvTitulo.setText(peli.titulo);
        TvSinopsis.setText(peli.sinopsis);

        Picasso.get().load(peli.img).into(TvImg);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retro = RetroFactor.Links();
                peliService service = retro.create(peliService.class);
                Call<Peliculas> call = service.idEtidPeli(peli.id);

                call.enqueue(new Callback<Peliculas>() {
                    @Override
                    public void onResponse(Call<Peliculas> call, Response<Peliculas> response) {
                        if (!response.isSuccessful()){
                            Log.e("App_Peliculas","ERROR APP");
                        }else {
                            Log.i("App_Peliculas","Voy a Detalles");
                            Log.i("App_Peliculas",new Gson().toJson(response.body()));
                            Intent intent = new Intent(view.getContext(), Detalles.class);

                            String animeJSON =new Gson().toJson(peli);
                            intent.putExtra("PELICULAS", animeJSON);
                            view.getContext().startActivity(intent);
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

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    class peliculaHolder extends RecyclerView.ViewHolder{

        public peliculaHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public void remove(int position) {
        peliculas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeInserted(position,peliculas.size());
    }
}
