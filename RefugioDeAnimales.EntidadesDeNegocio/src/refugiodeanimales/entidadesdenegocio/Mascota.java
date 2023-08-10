package refugiodeanimales.entidadesdenegocio;

import java.util.ArrayList;

public class Mascota {
    private int id;
    private int idTipo;
    private int idGenero;
    private String raza;
    private String nombre;
    private String imagenurl;
    private int top_aux;
    private Tipo tipo;
    private Genero genero;

    public Mascota() {
    }

    public Mascota(int id, int idTipo, int idGenero, String raza, String nombre, String imagenurl, int top_aux, Tipo tipo, Genero genero, ArrayList<Tipo> tipos) {
        this.id = id;
        this.idTipo = idTipo;
        this.idGenero = idGenero;
        this.raza = raza;
        this.nombre = nombre;
        this.imagenurl = imagenurl;
        this.top_aux = top_aux;
        this.tipo = tipo;
        this.genero = genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagenurl() {
        return imagenurl;
    }

    public void setImagenurl(String imagenurl) {
        this.imagenurl = imagenurl;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }
        
    
}
