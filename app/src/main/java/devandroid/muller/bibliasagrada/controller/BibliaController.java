package devandroid.muller.bibliasagrada.controller;

import android.content.SharedPreferences;

import java.util.List;

import devandroid.muller.bibliasagrada.view.Activity_biblia;
import devandroid.muller.bibliasagrada.database.BibliaDB;
import devandroid.muller.bibliasagrada.model.Livro;
import devandroid.muller.bibliasagrada.model.Versiculo;
import devandroid.muller.bibliasagrada.view.MainActivity;

public class BibliaController extends BibliaDB {


    SharedPreferences preferences;
    SharedPreferences.Editor dadosPreferencias;
    public static final String NOME_PREFERENCES = "pref_biblia";


    public BibliaController(MainActivity activity) {
        super(activity);

        copyDatabase(activity);

    }

    public BibliaController(Activity_biblia activity_biblia) {
        super(activity_biblia);

        preferences = activity_biblia.getSharedPreferences(NOME_PREFERENCES,0);
        dadosPreferencias = preferences.edit();
    }

    public void salvar(Versiculo versiculo) {

        dadosPreferencias.putInt("livro", versiculo.getNumeroDoLivro());
        dadosPreferencias.putInt("capitulo", versiculo.getNumeroDoCapitulo());
        dadosPreferencias.apply();
    }

    public int recuperarLivroCapituloPreferences(String chave, int valorPadrao){

        return preferences.getInt(chave,valorPadrao);
    }

    public int consultaTotalRegistros(){

        return quantidadeDeRegistros();
    }

    public List<Livro> buscarListaDeLivros(){

        return listaDeLivros();
    }

    public List<Versiculo> buscarVersiculos(int codigoLivro, int codigoVersiculo){

        return listaDeVersiculos(codigoLivro, codigoVersiculo);

    }

    public List<Versiculo> buscarTextoDoVersiculo(int idLivro, int numeroCapitulo){

        return textoDoVersiculo(idLivro, numeroCapitulo);
    }

    public boolean verificaSeExisteRegistros(int idLivro, int numeroCapitulo){

        return existeRegistros(idLivro,numeroCapitulo);
    }

    public boolean verificaSeCapituloFoiLido(int idLivro, int numeroCapitulo){

        return verificaLeituraCapitulo(idLivro,numeroCapitulo);
    }

    public void alterarValorColunaLido (int idLivro, int numeroCapitulo, int lido){

        alterarLeituraCapitulo(idLivro, numeroCapitulo, lido);
    }

}
