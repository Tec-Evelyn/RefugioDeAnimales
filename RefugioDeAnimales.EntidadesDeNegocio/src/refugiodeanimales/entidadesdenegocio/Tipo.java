package refugiodeanimales.entidadesdenegocio;

public class Tipo {
  private int id;
  private String nombre;
  private byte estatus;
  private int top_aux;

    public Tipo() {
    }

    public Tipo(int id, String nombre, byte estatus, int top_aux) {
        this.id = id;
        this.nombre = nombre;
        this.estatus = estatus;
        this.top_aux = top_aux;
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

    public byte getEstatus() {
        return estatus;
    }

    public void setEstatus(byte estatus) {
        this.estatus = estatus;
    }

    public int getTop_aux() {
        return top_aux;
    }

    public void setTop_aux(int top_aux) {
        this.top_aux = top_aux;
    }
  
  
}
