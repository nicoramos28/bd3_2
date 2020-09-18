package src.com.practico2.api;

public class Resultado {

    private int cedula;

    private String codigo;

    private int calificacion;

    public Resultado(int cedula, String codigo, int calificacion) {
        this.cedula = cedula;
        this.codigo = codigo;
        this.calificacion = calificacion;
    }

    public int getCedula() {
        return cedula;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }
}
