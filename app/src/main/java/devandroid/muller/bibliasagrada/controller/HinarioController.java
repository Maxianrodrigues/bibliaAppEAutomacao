package devandroid.muller.bibliasagrada.controller;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import devandroid.muller.bibliasagrada.database.HinarioDB;
import devandroid.muller.bibliasagrada.model.Hinario;
import devandroid.muller.bibliasagrada.view.Activity_biblia;
import devandroid.muller.bibliasagrada.view.Activity_hinario;
import devandroid.muller.bibliasagrada.view.MainActivity;

public class HinarioController extends HinarioDB {

    //criando uma variavel de nome preferences do tipo de objeto SharedPreferences
    SharedPreferences preferences;
    //criando uma variavel de nome dadosPreferencia do tipo de objeto SharedPreferences.Editor para manipular o dados, deletando, incluindo ou alterando
    SharedPreferences.Editor dadosPreferencia;
    //criandao uma variavel com nome final NOME_PREFRENCES onde dará ao nome de arquivo pref_hinario, como é uma static final, garante que isso não mudará
    public static final String NOME_PREFRENCES = "pref_hinario";


    public HinarioController(Activity_hinario activity_hinario) {
        super(activity_hinario);

        preferences = activity_hinario.getSharedPreferences(NOME_PREFRENCES,0);
        dadosPreferencia = preferences.edit();
    }

    public HinarioController(MainActivity activity) {
        super(activity);

        copyDatabase(activity);
    }

    public void salvarPreferences(Hinario hinario){

        dadosPreferencia.putInt("NumeroHino",hinario.getId());
        dadosPreferencia.apply();
    }

    public int recuperarNumeroDoHinoPref(String chave, int valorPadrao){

        return preferences.getInt(chave,valorPadrao);
    }


    public List<Hinario> buscarListaDeHinos(){

        return listaDeHinos();
    }

    public List<Hinario> buscarTextoDosHinos(String descricao){

        return textoDosHinos(descricao);
    }

    public List<String> listaDeHinarios(){

        List<String> lista = new ArrayList<>();

        String harpaCrista = "HC";
        //String outroHino = "OUT";
        lista.add(harpaCrista);
       // lista.add(outroHino);

        return lista;

    }


    public Hinario hinoFiltradoPorId(int id){

        return buscarPorId(id);
    }
}
