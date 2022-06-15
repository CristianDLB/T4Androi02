package com.example.t4androi02.BaseDatos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.t4androi02.Entidad.Peliculas;

import java.util.List;

@Dao
public interface PelisDao {

    @Query("SELECT * FROM peliculasTabla")
    List<Peliculas> getTodo();

    @Insert
    void Crear(Peliculas peliculas);
}
