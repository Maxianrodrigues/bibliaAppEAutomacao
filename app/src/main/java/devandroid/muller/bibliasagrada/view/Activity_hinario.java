package devandroid.muller.bibliasagrada.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import devandroid.muller.bibliasagrada.R;
import devandroid.muller.bibliasagrada.controller.HinarioController;
import devandroid.muller.bibliasagrada.model.Hinario;

public class Activity_hinario extends AppCompatActivity {



    HinarioController hinarioController;

    List<Hinario> listaDeHinos;
    List<Hinario> textoDoHino;
    List<String> listaHinarios;
    Hinario numeroHino;
    int hinoId;
    int proximoHino;
    int hinoRecuperado;
    int hinoAnterior;
    Hinario hinario;


    SearchView search_hinos;
    RecyclerView recyclerViewHinos;
    TextView txtTitulo;
    TextView txthinos;
    TextView txtNomeDoHino;
    TextView txtNumeroDoHino;
    Spinner spinnerHinos;
    EditText editBuscaId;
    ImageButton imgSetaEsquerdaHino;
    ImageButton imgSetaDireitaHino;
    ImageButton imgBack;
    Spinner spinnerHinarios;
    ImageButton imgZoomMais;
    ImageButton imgZoomMenos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_hinario);

        inicializarComponentesTela();

        hinarioController = new HinarioController(this);

        listaHinarios = hinarioController.listaDeHinarios();
        listaDeHinos = hinarioController.buscarListaDeHinos();
        numeroHino = hinarioController.hinoFiltradoPorId(2);
        txthinos.setText(numeroHino.getHino());
        txtTitulo.setText("Harpa Cristã");

        //chamando a controladora para recuperar do sharedPreferences o numero do ultimo hino filtrado pelo usuário
        hinoRecuperado = hinarioController.recuperarNumeroDoHinoPref("NumeroHino", 1);

        ArrayAdapter<Hinario> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaDeHinos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHinos.setAdapter(adapter);

        //definindo no spinner o hino recuperado pela controladora através da variavel hinoRecuperado
        definirirHinoPrefences();

        spinnerHinos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Hinario hinarioSelecionado = (Hinario) adapterView.getItemAtPosition(position);
                hinoId = hinarioSelecionado.getId();

                //salvando no preferences o numero do hino selecionado
                hinarioController.salvarPreferences(hinarioSelecionado);

                //Toast.makeText(Activity_hinario.this, "Livro selecionado: " + hinarioSelecionado.getTitulo(), Toast.LENGTH_SHORT).show();


                txtNumeroDoHino.setText(hinarioSelecionado.getTitulo());
                textoDoHino = hinarioController.buscarTextoDosHinos(hinarioSelecionado.getTitulo());

                //convertendo a lista textDoHino em string para que possa ser exbidida no textview
                //StringBuilder é uma classe em Java que é usada para criar strings mutáveis,
                //o que significa que você pode modificar seu conteúdo após a criação. Em Java,
                // strings são imutáveis, o que significa que uma vez criadas, elas não podem ser alteradas.
                // Isso pode ser ineficiente em operações de concatenação de strings, porque cada concatenação cria uma nova string.
                StringBuilder stringBuilder = new StringBuilder();
                for (Hinario hinario : textoDoHino){

                    //em cada laço eu chamo esse metodo converter para que linha a linha eu faça o replace das tags xml
                    stringBuilder.append(converter(hinario.getHino()));
                }

                String hinoParaExibir = stringBuilder.toString();
                System.out.println("Xml para processar" + hinoParaExibir);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                    txthinos.setText(Html.fromHtml(hinoParaExibir, Html.FROM_HTML_MODE_LEGACY));
                }else {
                    txthinos.setText(Html.fromHtml(hinoParaExibir));
                }



                //String textoPuro = UtilExtraiTextoXml.extraiTextoDoXml(hinoParaExibir);

                //txthinos.setText(hinoParaExibir);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listaHinarios);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHinarios.setAdapter(adapter1);

        spinnerHinarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        editBuscaId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence filtro, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable filtro) {

                //se o filtro for 0, ou seja vazio eu apresento um log e não faço nada
                if (filtro.length() == 0){
                    Log.i("ValorFiltro", "vazio " + filtro);
                }else {
                    //se não for 0, eu chamo o metodo filtrarHinosPorId passando o valor  digitado pelo usuario
                    filtrarHinosPorId(filtro.toString());
                }

            }
        });

        //textoDoHino = hinarioController.buscarTextoDosHinos("Chuvas De Graça");

        Animator animatorSetaDireita = ObjectAnimator.ofFloat(imgSetaDireitaHino, "scaleX", 1.0f, 0.8f, 1.0f);
        animatorSetaDireita.setDuration(100);

        imgSetaDireitaHino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorSetaDireita.start();
                imgSetaEsquerdaHino.setEnabled(true);
                proximoHino = hinoId + 1;

                if (proximoHino <=640){
                    spinnerHinos.setSelection(proximoHino -1);

                }else {
                    Toast.makeText(Activity_hinario.this, "Não existe hino maior que o hino: HC640", Toast.LENGTH_SHORT).show();
                    imgSetaDireitaHino.setEnabled(false);
                }

            }
        });

        Animator animatorSetaEsquerda = ObjectAnimator.ofFloat(imgSetaEsquerdaHino,"scaleX",1.0f, 0.8f, 1.0f);
        animatorSetaEsquerda.setDuration(100);

        imgSetaEsquerdaHino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //hinoAnterior = hinoId -2;
                animatorSetaEsquerda.start();
                imgSetaDireitaHino.setEnabled(true);

                if (hinoId  >1){
                    spinnerHinos.setSelection(hinoId -2);
                }else {

                    Toast.makeText(Activity_hinario.this, "Não existe hino inferior ao hino: HC001", Toast.LENGTH_SHORT).show();
                    imgSetaEsquerdaHino.setEnabled(false);
                }


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Animator animatorZoomMais = ObjectAnimator.ofFloat(imgZoomMais,"ScaleX", 1.0f, 0.8f, 1.0f);
        animatorZoomMais.setDuration(100);

        imgZoomMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorZoomMais.start();

                imgZoomMenos.setEnabled(true);
                float tamanhoAtual = txthinos.getTextSize();
                float novoTamanho = tamanhoAtual + 10;

                if (novoTamanho <=100){
                    txthinos.setTextSize(TypedValue.COMPLEX_UNIT_PX, novoTamanho);
                    Log.i("fonte", "novo tamanho de fonte definido " + novoTamanho);
                }else {
                    Toast.makeText(Activity_hinario.this, "Limite de aumento de fonte atingido", Toast.LENGTH_SHORT).show();
                    imgZoomMais.setEnabled(false);
                }

            }
        });

        Animator animatorZoomMenos = ObjectAnimator.ofFloat(imgZoomMenos, "ScaleX", 1.0f, 0.8f, 1.0f);
        animatorZoomMenos.setDuration(100);

        imgZoomMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animatorZoomMenos.start();

                imgZoomMais.setEnabled(true);
                float tamanhoAtualmente = txthinos.getTextSize();
                float novoTamanhoFonte = tamanhoAtualmente - 10;

                if (novoTamanhoFonte >=5){
                    txthinos.setTextSize(TypedValue.COMPLEX_UNIT_PX, novoTamanhoFonte);
                    Log.i("finte", "novo tamanho de fonte definido");
                }else {
                    Toast.makeText(Activity_hinario.this, "limite de redução de fonte atingido", Toast.LENGTH_SHORT).show();
                    imgZoomMenos.setEnabled(false);
                }
            }
        });

    }

    private void definirirHinoPrefences() {

        spinnerHinos.setSelection(hinoRecuperado -1);
    }

    private void filtrarHinosPorId(String filtro) {

        int limitador = Integer.parseInt(filtro);

        if (limitador > 640 || limitador == 0){
            Toast.makeText(this, "Não existe hino superior 640!", Toast.LENGTH_SHORT).show();
        }else {

            if (TextUtils.isEmpty(filtro)){

                Toast.makeText(this, "Não existe hino com essa numeração!", Toast.LENGTH_SHORT).show();
            }else {
                Hinario hinoPorIdFiltrado = hinarioController.buscarPorId(Integer.parseInt(filtro));

                if (hinoPorIdFiltrado == null){
                    Hinario erroHinoId = new Hinario();
                    erroHinoId.setHino("Nenhum hino encontrado");
                    txthinos.setText((CharSequence) erroHinoId);

                }else {
                    int hinoIdBusca = Integer.parseInt(filtro);
                    spinnerHinos.setSelection(Integer.parseInt(String.valueOf(hinoIdBusca -1)));
                }
            }
        }


    }

    //metodo para realizar o replace das tags xml por valores vazios ou quebras de linha
    private String converter(String hino) {

        String textoComNegrito = hino.replace("]]></verse><verse label=\"1\" type=\"c\">", "<br><br><b>")
                .replace("]]></verse><verse label=\"2\" type=\"v\">", "</b><br><br>")
                .replace("]]></verse><verse label=\"2\" type=\"c\">", "<b><br><br>")
                .replace("]]></verse><verse label=\"3\" type=\"v\">", "</b><br><br>");


        String textoPuro = textoComNegrito.replace("<![CDATA[", "").replace("]]></verse><verse label=\"1\" type=\"c\">", "<br><br>")
                .replace("<song version=\"1.0\"><lyrics><verse label=\"1\" type=\"v\">","").replace("<?xml version='1.0' encoding='UTF-8'?>","")
                .replace("]]></verse><verse label=\"2\" type=\"v\">","<br><br>").replace("]]></verse><verse label=\"3\" type=\"v\">","<br><br>")
                .replace("]]></verse><verse label=\"4\" type=\"v\">","<br><br>").replace("]]></verse></lyrics></song>","")
                .replace("]]></verse><verse label=\"5\" type=\"v\">","<br><br>").replace("]]></verse><verse label=\"6\" type=\"v\">","<br><br>")
                .replace("]]></verse><verse label=\"7\" type=\"v\">","<br><br>").replace("]]></verse><verse label=\"8\" type=\"v\">","<br><br>");

        return textoPuro;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja retornar para tela inicial ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }

    public void inicializarComponentesTela(){

        txtTitulo =  findViewById(R.id.txtTitulo);
        txthinos = findViewById(R.id.txthinos);
        txtNumeroDoHino = findViewById(R.id.txtNumeroDoHino);
        txtNomeDoHino = findViewById(R.id.txtNomeDoHino);
        spinnerHinos = findViewById(R.id.spinnerHinos);
        editBuscaId = findViewById(R.id.editBuscaId);
        imgSetaEsquerdaHino = findViewById(R.id.imgSetaEsquerdaHino);
        imgSetaDireitaHino = findViewById(R.id.imgSetaDireitaHino);
        imgBack = findViewById(R.id.imgBack);
        spinnerHinarios =  findViewById(R.id.spinnerHinarios);
        imgZoomMais = findViewById(R.id.imgZoomMais);
        imgZoomMenos = findViewById(R.id.imgZoomMenos);

    }
}