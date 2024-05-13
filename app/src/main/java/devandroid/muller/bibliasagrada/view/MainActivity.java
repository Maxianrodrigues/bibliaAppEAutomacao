package devandroid.muller.bibliasagrada.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import devandroid.muller.bibliasagrada.R;
import devandroid.muller.bibliasagrada.controller.BibliaController;
import devandroid.muller.bibliasagrada.controller.HinarioController;

public class MainActivity extends AppCompatActivity {

    BibliaController bibliaController;
    HinarioController hinarioController;

    ImageView imgBiblia;
    ImageView imgHinario;

    int totalDeRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_main);

        bibliaController = new BibliaController(this);
        hinarioController = new HinarioController(this);

        //totalDeRegistros = bibliaController.consultaTotalRegistros();


        inicializarComponentesTela();


        Animator animator = ObjectAnimator.ofFloat(imgBiblia, "scaleX", 1.0f, 0.8f, 1.0f);
        animator.setDuration(100);

        imgBiblia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animator.start();

                Intent telaBiblia = new Intent(getBaseContext(), Activity_biblia.class);
                startActivity(telaBiblia);

            }
        });

        imgHinario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent telaHinario = new Intent(getBaseContext(), Activity_hinario.class);
                startActivity(telaHinario);
            }
        });
    }


    private void inicializarComponentesTela() {

        imgBiblia = findViewById(R.id.imgBiblia);
        imgHinario =  findViewById(R.id.imgHinario);
    }
}