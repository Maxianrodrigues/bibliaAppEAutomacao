package devandroid.muller.bibliasagrada.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import devandroid.muller.bibliasagrada.R;

public class SplashActivity extends AppCompatActivity {

    public static final int TIME_OUT_SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        cumutarTelaSplash();
    }

    private void cumutarTelaSplash() {

        //metodo para executar uma tarefa após o parametro definido em milesegundos, que nesse caso definimos na constante public static final int TIME_OUT_SPLASH
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, TIME_OUT_SPLASH);
    }
}