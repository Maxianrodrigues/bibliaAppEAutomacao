package devandroid.muller.pageObjects;

import android.widget.TextView;

import androidx.test.rule.ActivityTestRule;

import com.adevinta.android.barista.assertion.BaristaCheckedAssertions;
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions;
import com.adevinta.android.barista.interaction.BaristaClickInteractions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import devandroid.muller.bibliasagrada.R;
import devandroid.muller.bibliasagrada.view.MainActivity;

public class AbrirAppBiblia {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void abrirAppComsucesso(){
        BaristaVisibilityAssertions.assertDisplayed(R.id.txtTitulo);
        BaristaVisibilityAssertions.assertDisplayed(R.id.imgBiblia);
    }

    protected void acessarBiblia(){
        BaristaClickInteractions.clickOn(R.id.imgBiblia);
        BaristaVisibilityAssertions.assertDisplayed(R.id.spinnerLivros);
    }

    protected void marcarCapituloComoLido(){
        BaristaClickInteractions.clickOn(R.id.chkLido);
        BaristaCheckedAssertions.assertChecked(R.id.chkLido);
    }

    protected void voltarTelaInicial(){
        BaristaClickInteractions.clickOn(R.id.imgBack);
    }
}
