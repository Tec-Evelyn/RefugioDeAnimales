package refugiodeanimales.entidadesdenegocio;

import java.util.ArrayList;

public class Genero {
    private int id;
    private String nombre;
    private int top_aux;
    private ArrayList<Mascota> mascotas;

    public Genero() {
    }

    public Genero(int id, String nombre, int top_aux, ArrayList<Mascota> mascotas) {
        this.id = id;
        this.nombre = nombre;
        this.top_aux = top_aux;
        this.mascotas = mascotas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public ArrayList<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
    
}
