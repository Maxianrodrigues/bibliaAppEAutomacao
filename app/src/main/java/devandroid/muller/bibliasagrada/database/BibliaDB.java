package devandroid.muller.bibliasagrada.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import devandroid.muller.bibliasagrada.model.Livro;
import devandroid.muller.bibliasagrada.model.Versiculo;

public class BibliaDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "NVT.sqlite";
    public static final int DB_VERSION = 1;


    Cursor cursor;
    SQLiteDatabase db;

    public BibliaDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);


        db = getWritableDatabase();
    }

    //método para copiar o banco de dados NVT.sqlite da pasta assets para pasta raiz da aplicação
    //nesse metodo vamos substituir o banco criado pelo banco já populado com os dados dos livros, capitulos e versiculos
    public void copyDatabase(Context context) {
        try {
            InputStream inputStream = context.getAssets().open(DB_NAME);
            String outrFileName = context.getDatabasePath(DB_NAME).getPath();
            OutputStream outputStream = new FileOutputStream(outrFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //criando a tabela book passando IF NOT EXISTS para caso já existir, não truncar a tabela
        String sqlTabelaBook
                = "CREATE TABLE IF NOT EXISTS book (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_reference_id  INTEGER, " +
                "testament_reference_id INTEGER, " +
                "name TEXT )";

        db.execSQL(sqlTabelaBook);

        String sqlTabelaVerse
                = "CREATE TABLE IF NOT EXISTS verse (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "book_id INTEGER, " +
                "chapter INTEGER, " +
                "verse INTEGER, " +
                "text TEXT )";

        db.execSQL(sqlTabelaVerse);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }

    public int quantidadeDeRegistros() {

        String querySQL = "select count(id) as total from book";

        cursor = db.rawQuery(querySQL, null);

        int quantidade = 0;

        if (cursor != null && cursor.moveToFirst()) {
            quantidade = cursor.getInt(cursor.getColumnIndex("total"));
        }

        if (cursor != null) {

            cursor.close();
        }

        return quantidade;
    }

    //realizando uma consulta que devolve uma lista com id e nome do livro
    public List<Livro> listaDeLivros() {

        List<Livro> lista = new ArrayList<>();

        Livro registro;

        String querySQL = "select book_reference_id, name from book";

        cursor = db.rawQuery(querySQL, null);

        if (cursor.moveToFirst()){

            do {

                registro = new Livro();
                registro.setId(cursor.getInt(0));
                registro.setNomeDoLivro(cursor.getString(1));

                lista.add(registro);
            }while (cursor.moveToNext());

        }else {

        }

        return lista;
    }

    //metodo criado para obter uma lista de capitulos do livro selecionado sendo que no codigo versiculo
    //sempre vou passar o valor default 1
    public List<Versiculo> listaDeVersiculos(int codigoLivro, int codigoVersiculo){

        List<Versiculo> lista = new ArrayList<>();

        Versiculo registro;

        String querySQL = "select * from verse where book_id = ? and verse = ?  order by chapter asc";
        String[] args = new String[]{String.valueOf(codigoLivro), String.valueOf(codigoVersiculo)};

        cursor = db.rawQuery(querySQL,args);

        if (cursor.moveToFirst()){

            do {
                registro = new Versiculo();
                registro.setId(cursor.getInt(0));
                registro.setNumeroDoLivro(cursor.getInt(1));
                registro.setNumeroDoCapitulo(cursor.getInt(2));
                registro.setNumeroDoVersiculo(cursor.getInt(3));
                registro.setLido(cursor.getInt(5));

                lista.add(registro);

            }while (cursor.moveToNext());

        }else {

        }
        return lista;
    }

    public List<Versiculo> textoDoVersiculo(int idLivro, int numeroCapitulo){

        List<Versiculo> lista = new ArrayList<>();

        Versiculo registro;

        String querySQL = "select * from verse where book_id = ? and chapter = ?";
        String[] args = new String[]{String.valueOf((idLivro)), String.valueOf((numeroCapitulo))};

        cursor = db.rawQuery(querySQL,args);

        if (cursor.moveToFirst()){

            do {
                registro = new Versiculo();
                registro.setNumeroDoVersiculo(cursor.getInt(3));
                registro.setTextoDoVersiculo(cursor.getString(4));
                lista.add(registro);

            }while (cursor.moveToNext());
        }else {

        }

        return lista;
    }

    public boolean existeRegistros(int idLivro, int numeroCapitulo){


        String querySQL = "select count(chapter) as total from verse where book_id = ? and chapter = ?";
        //String[] args = new String[]{String.valueOf(idLivro), String.valueOf(numeroCapitulo)};

        boolean registrosPositivos = false;
        int quantidadeRegistros = 0;

        cursor = null;
        try {
            cursor = db.rawQuery(querySQL, new String[]{String.valueOf(idLivro), String.valueOf(numeroCapitulo)});

            if (cursor != null && cursor.moveToFirst()) {
                quantidadeRegistros = cursor.getInt(cursor.getColumnIndex("total"));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (quantidadeRegistros >=1){

            registrosPositivos = true;
        }else {

            registrosPositivos = false;
        }

        return registrosPositivos;
    }

    public boolean verificaLeituraCapitulo(int idLivro, int numeroCapitulo){

        String querySql = "select * from verse where book_id = ? and chapter = ? and verse = 1";
        String[] args = new String[]{String.valueOf(idLivro), String.valueOf(numeroCapitulo)};

        int valorColunaLido = 0;
        boolean capituloLido =  false;

        cursor =  null;

        try {
            cursor = db.rawQuery(querySql,args);

            if (cursor != null && cursor.moveToFirst()){
                valorColunaLido = cursor.getInt(5);
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        if (valorColunaLido == 1){

            capituloLido = true;
        }else {
            capituloLido = false;
        }

        return capituloLido;
    }

    public void alterarLeituraCapitulo(int idLivro, int numeroCapitulo, int lido){

        String querySql = "update verse set lido = ? where book_id = ? and chapter = ? and verse = 1";
        String[] args = new String[]{String.valueOf(lido), String.valueOf(idLivro), String.valueOf(numeroCapitulo)};

        db.execSQL(querySql, args);

    }
}
