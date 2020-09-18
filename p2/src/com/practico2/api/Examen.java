package src.com.practico2.api;

public class Examen {

    private String codigo;

    private String materia;

    private String periodo;


    public Examen(String codigo, String materia, String periodo) {
        this.codigo = codigo;
        this.materia = materia;
        this.periodo = periodo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getMateria() {
        return materia;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}
