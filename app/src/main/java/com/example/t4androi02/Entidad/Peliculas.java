package com.example.t4androi02.Entidad;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "peliculasTabla")
public class Peliculas {

    public String titulo;
    public String sinopsis;
    public String img;
    @PrimaryKey(autoGenerate = true)
    public int id;

    public Peliculas(){}

    public Peliculas(String titulo, String sinopsis, String img, int id) {
        this.titulo = titulo;
        this.sinopsis = sinopsis;
        this.img = img;
        this.id = id;
    }
}
