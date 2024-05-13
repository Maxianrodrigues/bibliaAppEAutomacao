package devandroid.muller.pageObjects;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.adevinta.android.barista.assertion.BaristaAssertions;
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions;
import com.adevinta.android.barista.interaction.BaristaClickInteractions;
import com.adevinta.android.barista.interaction.BaristaEditTextInteractions;
import com.adevinta.android.barista.interaction.BaristaSleepInteractions;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import devandroid.muller.bibliasagrada.R;
import devandroid.muller.bibliasagrada.view.Activity_biblia;
import devandroid.muller.bibliasagrada.view.MainActivity;

//uma classe abstrata não pode ser instanciada, apenas herdada
public  class AbrirHinario {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void abrirHinarioComSucesso(){
        //vou clicar no modulo hinario
        BaristaVisibilityAssertions.assertDisplayed(R.id.txtTitulo);
        BaristaClickInteractions.clickOn(R.id.imgHinario);
    }

    protected void buscaPorCodigoHino(String codigo){
        BaristaVisibilityAssertions.assertDisplayed(R.id.txtTitulo,"Harpa Cristã");
        BaristaVisibilityAssertions.assertDisplayed(R.id.txtNomeDoHino);
        BaristaEditTextInteractions.writeTo(R.id.editBuscaId, codigo);
        BaristaClickInteractions.clickOn(R.id.spinnerHinos);
        BaristaClickInteractions.clickBack();
        //chamando metodos nativos do Espresso.
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
    }
}
/*

    @Test
    public void telaComHinosVisiveis(){
        //BaristaSleepInteractions.sleep(5000);
        BaristaVisibilityAssertions.assertDisplayed(R.id.txtTitulo,"Harpa Cristã");
        BaristaVisibilityAssertions.assertDisplayed(R.id.txtNomeDoHino);
        BaristaEditTextInteractions.writeTo(R.id.editBuscaId, "343");
        BaristaClickInteractions.clickOn(R.id.spinnerHinos);
        //chamando metodos nativos do Espresso.
        Espresso.pressBack();
        Espresso.pressBack();
        Espresso.pressBack();
    }
*/

