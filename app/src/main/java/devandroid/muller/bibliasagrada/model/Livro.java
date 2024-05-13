package devandroid.muller.bibliasagrada.model;

public class Livro {

    private int id;
    private int idTestamento;
    private String nomeDoLivro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTestamento() {
        return idTestamento;
    }

    public void setIdTestamento(int idTestamento) {
        this.idTestamento = idTestamento;
    }

    public String getNomeDoLivro() {
        return nomeDoLivro;
    }

    public void setNomeDoLivro(String nomeDoLivro) {
        this.nomeDoLivro = nomeDoLivro;
    }

    // Sobrescreva o método toString para retornar o nome do livro
    @Override
    public String toString() {
        return nomeDoLivro;
    }

}
