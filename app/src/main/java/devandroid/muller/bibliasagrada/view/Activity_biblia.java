package devandroid.muller.bibliasagrada.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import devandroid.muller.bibliasagrada.R;
import devandroid.muller.bibliasagrada.controller.BibliaController;
import devandroid.muller.bibliasagrada.controller.HinarioController;
import devandroid.muller.bibliasagrada.model.Livro;
import devandroid.muller.bibliasagrada.model.Versiculo;

public class Activity_biblia extends AppCompatActivity {

    BibliaController bibliaController;

    List<Livro> livrosListados;
    List<Versiculo> capitulosListados;
    List<Versiculo> textoDoVersiculoSelec;
    int livroId;
    int capituloId;
    int proximoLivro;
    int proximoCapitulo;
    int capituloAnterior;
    int livroIdRecuperado;
    int capituloIdRecuperado;
    boolean retornoRegistro;
    boolean leitura;

    TextView txtBibliaLivros;
    Spinner spinnerLivros;
    Spinner spinnerCapitulos;
    TextView txtVersiculo;
    ImageView imgSetaEsquerda;
    ImageView imgSetaDireita;
    ImageView imgZoomMais;
    ImageView imgZoomMenos;
    ImageButton imgBack;
    CheckBox chkLido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_biblia);

        bibliaController = new BibliaController(this);
        livroIdRecuperado = bibliaController.recuperarLivroCapituloPreferences("livro",1);
        capituloIdRecuperado = bibliaController.recuperarLivroCapituloPreferences("capitulo",1);

        livrosListados = bibliaController.buscarListaDeLivros();


        txtBibliaLivros =  findViewById(R.id.txtBibliaLivros);
        spinnerLivros = findViewById(R.id.spinnerLivros);
        spinnerCapitulos =  findViewById(R.id.spinnerCapitulos);
        txtVersiculo = findViewById(R.id.txtVersiculo);
        imgSetaDireita = findViewById(R.id.imgSetaDireita);
        imgSetaEsquerda = findViewById(R.id.imgSetaEsquerda);
        imgZoomMais =  findViewById(R.id.imgZoomMais);
        imgZoomMenos = findViewById(R.id.imgZoomMenos);
        imgBack = findViewById(R.id.imgBack);
        chkLido = findViewById(R.id.chkLido);


        ArrayAdapter<Livro> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, livrosListados);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLivros.setAdapter(adapter);


        recuperandoDadosLivrosCapitulos();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        spinnerLivros.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {


                Livro livroSelecionado = (Livro) adapterView.getItemAtPosition(position);
                livroId = livroSelecionado.getId();
                capitulosListados = bibliaController.buscarVersiculos(livroId, 1);



                // Atualizar o ArrayAdapter do Spinner de capitulos
                ArrayAdapter<Versiculo> capitulosArrayAdapter = new ArrayAdapter<>(Activity_biblia.this, android.R.layout.simple_spinner_item, capitulosListados);
                capitulosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCapitulos.setAdapter(capitulosArrayAdapter);
                spinnerCapitulos.setSelection(capituloIdRecuperado -1);
                capituloIdRecuperado=1;

                //Toast.makeText(Activity_biblia.this, "Livro selecionado: " + livroSelecionado.getNomeDoLivro(), Toast.LENGTH_SHORT).show();

                txtBibliaLivros.setText("Livro " + livroSelecionado.getNomeDoLivro());


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinnerCapitulos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                Versiculo capituloSelecionado = (Versiculo) adapterView.getItemAtPosition(position);
                capituloId = capituloSelecionado.getNumeroDoCapitulo();

                //salvando no sharedPreferences os dados do livro e capitulo exibido em tela atualmente.
                bibliaController.salvar(capituloSelecionado);

                //Toast.makeText(Activity_biblia.this, "Capitulo Selecionado: " +capituloSelecionado.getNumeroDoCapitulo(), Toast.LENGTH_SHORT).show();

                textoDoVersiculoSelec = bibliaController.buscarTextoDoVersiculo(livroId, capituloId);

                StringBuilder stringBuilder = new StringBuilder();
                for (Versiculo versiculo : textoDoVersiculoSelec) {
                    stringBuilder.append(versiculo.getNumeroDoVersiculo() + ". " + versiculo.getTextoDoVersiculo()).append("\n \n"); // Adiciona o texto do versículo com uma quebra de linha
                }
                String textoParaExibir = stringBuilder.toString();
                txtVersiculo.setText(textoParaExibir);

                leitura = bibliaController.verificaSeCapituloFoiLido(livroId, capituloId);

                if (leitura){
                    chkLido.setChecked(true);
                }else {
                    chkLido.setChecked(false);
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Animator animatorZoomMais = ObjectAnimator.ofFloat(imgZoomMais,"scaleX",1.0f, 0.8f, 1.0f);
        animatorZoomMais.setDuration(100);

        imgZoomMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgZoomMenos.setEnabled(true);
                float tamanhoAtual = txtVersiculo.getTextSize(); // Obtém o tamanho atual da fonte
                float novoTamanho = tamanhoAtual + 10; // Aumenta o tamanho atual em 10 unidades de pixel

                if (novoTamanho <= 100) { // Verifica se o novo tamanho está dentro dos limites
                    txtVersiculo.setTextSize(TypedValue.COMPLEX_UNIT_PX, novoTamanho); // Aplica o novo tamanho ao TextView
                    Log.i("zoom", "Novo tamanho da fonte: " + novoTamanho);
                }else {
                    imgZoomMais.setEnabled(false);
                    Toast.makeText(Activity_biblia.this, "Limite de aumento atingido.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        Animator animatorZoomMenos = ObjectAnimator.ofFloat(imgZoomMenos,"scaleX",1.0f, 0.8f, 1.0f);
        animatorZoomMenos.setDuration(100);

        imgZoomMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgZoomMais.setEnabled(true);
                float tamanhoAtualmente = txtVersiculo.getTextSize();
                float novoTamanhofonte = tamanhoAtualmente - 10;

                if (novoTamanhofonte >=5){
                    txtVersiculo.setTextSize(TypedValue.COMPLEX_UNIT_PX,novoTamanhofonte);
                }else {
                    imgZoomMenos.setEnabled(false);
                    Toast.makeText(Activity_biblia.this, "Limite de redução de fonte atingido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Animator animator = ObjectAnimator.ofFloat(imgSetaDireita,"scaleX",1.0f, 0.8f, 1.0f);
        animator.setDuration(100);

        imgSetaDireita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtVersiculo.setTextColor(getColor(R.color.black));
                animator.start();


                //Toast.makeText(Activity_biblia.this, "Próxima página", Toast.LENGTH_SHORT).show();

                proximoCapitulo = capituloId + 1;
                //proximoCapitulo = capituloId;

                retornoRegistro = Boolean.parseBoolean(String.valueOf(bibliaController.verificaSeExisteRegistros(livroId,proximoCapitulo)));

                if (retornoRegistro){
                    spinnerCapitulos.setSelection(proximoCapitulo -1);
                    //spinnerCapitulos.setSelection(proximoCapitulo);
                }else {

                    Log.i("Proximo capitulo", "numero do proximo capitutlo: " + livroId);

                    if (livroId <= 65){

                        proximoLivro = livroId;
                        spinnerLivros.setSelection(proximoLivro);
                    }else {
                        spinnerLivros.setSelection(0);
                    }

                }

                //textoDoVersiculoSelec = bibliaController.buscarTextoDoVersiculo(livroId,proximoCapitulo);

                //ArrayAdapter<Versiculo> capitulosArrayAdapter = new ArrayAdapter<>(Activity_biblia.this, android.R.layout.simple_spinner_item, textoDoVersiculoSelec);
                //capitulosArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                //spinnerCapitulos.setAdapter(capitulosArrayAdapter);

                //seleciona o primeiro item da lista(indice 0)
                //spinnerCapitulos.setSelection(0);


            }
        });


        Animator animatorSetaEsquerda = ObjectAnimator.ofFloat(imgSetaEsquerda,"scaleX",1.0f, 0.8f, 1.0f);
        animator.setDuration(100);

        imgSetaEsquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtVersiculo.setTextColor(getColor(R.color.black));
                animatorSetaEsquerda.start();


                //Toast.makeText(Activity_biblia.this, "Página anterior", Toast.LENGTH_SHORT).show();

                capituloAnterior = capituloId;

                retornoRegistro = Boolean.parseBoolean(String.valueOf(bibliaController.verificaSeExisteRegistros(livroId,capituloAnterior)));

                if (retornoRegistro && capituloId > 1){
                    spinnerCapitulos.setSelection(capituloAnterior -2);
                    Log.i("CAPSELEC", "onClick: capitulo atual e livro: " + livroId + " capitulo: " + capituloAnterior);
                }

                if (retornoRegistro && capituloId == 1){
                    spinnerLivros.setSelection(livroId -2);
                    Log.i("CAPSELEC", "onClick: capitulo atual e livro: " + livroId + " capitulo: " + capituloAnterior);
                }

                if (retornoRegistro && livroId == 1 && capituloId == 1){
                    Toast.makeText(Activity_biblia.this, "Não existe livro anterior ao livro de Gênesis", Toast.LENGTH_SHORT).show();
                }

            }
        });

        chkLido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (chkLido.isChecked()){
                    bibliaController.alterarValorColunaLido(livroId, capituloId, 1);
                }else {
                    bibliaController.alterarLeituraCapitulo(livroId,capituloId, 0);
                }
            }
        });

    }

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

    public void recuperandoDadosLivrosCapitulos(){

        //spinnerCapitulos.setSelection(capituloIdRecuperado -1);
        spinnerLivros.setSelection(livroIdRecuperado -1);
    }
}