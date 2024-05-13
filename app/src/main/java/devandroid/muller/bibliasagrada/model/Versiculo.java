package devandroid.muller.bibliasagrada.model;

public class Versiculo {

    private int id;
    private int numeroDoLivro;
    private int numeroDoCapitulo;
    private int numeroDoVersiculo;
    private String textoDoVersiculo;
    private int lido;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroDoLivro() {
        return numeroDoLivro;
    }

    public void setNumeroDoLivro(int numeroDoLivro) {
        this.numeroDoLivro = numeroDoLivro;
    }

    public int getNumeroDoCapitulo() {
        return numeroDoCapitulo;
    }

    public void setNumeroDoCapitulo(int numeroDoCapitulo) {
        this.numeroDoCapitulo = numeroDoCapitulo;
    }

    public int getNumeroDoVersiculo() {
        return numeroDoVersiculo;
    }

    public void setNumeroDoVersiculo(int numeroDoVersiculo) {
        this.numeroDoVersiculo = numeroDoVersiculo;
    }

    public String getTextoDoVersiculo() {
        return textoDoVersiculo;
    }

    public void setTextoDoVersiculo(String textoDoVersiculo) {
        this.textoDoVersiculo = textoDoVersiculo;
    }

    public int isLido() {
        return lido;
    }

    public void setLido(int lido) {
        this.lido = lido;
    }

    @Override
    public String toString() {
        return String.valueOf(numeroDoCapitulo);
    }
}
