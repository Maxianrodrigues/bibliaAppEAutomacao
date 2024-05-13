package devandroid.muller.bibliasagrada.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import devandroid.muller.bibliasagrada.model.Hinario;

public class HinarioDB extends SQLiteOpenHelper {

    public static final String DB_NAME="hc.sqlite";
    public static final int DB_VERSION = 1;

    SQLiteDatabase db;
    Cursor cursor;

    public HinarioDB(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);

        db = getWritableDatabase();
    }

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

        String sqlTabelaSongs
                = "CREATE TABLE IF NOT EXISTS songs (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "alternate_title TEXT, " +
                "lyrics TEXT, " +
                "verse_order TEXT, " +
                "copyright TEXT, " +
                "comments TEXT, " +
                "ccli_number INTEGER, " +
                "theme_name TEXT, " +
                "search_title TEXT, " +
                "search_lyrics TEXT, " +
                "create_date TEXT, " +
                "last_date TEXT, " +
                "temporary INTEGER)";

        db.execSQL(sqlTabelaSongs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public List<Hinario> listaDeHinos(){

        List<Hinario> lista = new ArrayList<>();

        Hinario registro;

        String querySql = "select id, title from songs";

        cursor = db.rawQuery(querySql,null);

        if (cursor.moveToFirst()){

            do {
                registro = new Hinario();
                registro.setId(cursor.getInt(0));
                registro.setTitulo(cursor.getString(1));

                lista.add(registro);
            }while (cursor.moveToNext());
        }else {

        }
        return lista;
    }

    public List<Hinario> textoDosHinos(String descricao){

        List<Hinario> lista = new ArrayList<>();
        Hinario registro;

        String querySql = "select * from songs where title like ?";
        String[] args = new String[]{"%" + descricao + "%"};

        cursor = db.rawQuery(querySql,args);

        if (cursor.moveToFirst()){

            do {
                registro = new Hinario();
                registro.setHino(cursor.getString(3));

                lista.add(registro);
            }while (cursor.moveToNext());
        }else {

        }
        return lista;
    }

    public Hinario buscarPorId(int id){

        Hinario idHino = null;

        String querySql = "select * from songs where id=?";
        String[] args = new String[]{String.valueOf(id)};

        cursor = null;

        try {
            cursor = db.rawQuery(querySql,args);

            if (cursor.moveToFirst()){
                idHino = new Hinario();
                idHino.setId(cursor.getInt(0));
                idHino.setTitulo(cursor.getString(1));
                idHino.setHino(cursor.getString(3));
            }
        } catch (Exception excecao) {
            excecao.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return idHino;
    }
}
