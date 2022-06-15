package com.example.t4androi02.BaseDatos;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.t4androi02.Entidad.Peliculas;

@Database(entities = {Peliculas.class}, version = 1)
public abstract class AppDatos extends RoomDatabase {
    public abstract PelisDao userDao();

    public static AppDatos getDatabase (Context context){
        return Room.databaseBuilder(context,AppDatos.class,"databaseDB.peliculasDB")
                .allowMainThreadQueries()
                .build();

    }

}
