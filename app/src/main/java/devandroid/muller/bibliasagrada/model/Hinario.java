package devandroid.muller.bibliasagrada.model;

public class Hinario {
    private int id;
    private String titulo;
    private String hino;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getHino() {
        return hino;
    }

    public void setHino(String hino) {
        this.hino = hino;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
