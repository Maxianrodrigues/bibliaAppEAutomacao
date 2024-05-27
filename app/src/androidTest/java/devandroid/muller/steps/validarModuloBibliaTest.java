package devandroid.muller.steps;

import org.junit.Test;

import devandroid.muller.pageObjects.AbrirAppBiblia;
import devandroid.muller.pageObjects.AbrirHinario;

public class validarModuloBibliaTest extends AbrirAppBiblia {
    AbrirHinario hinario = new AbrirHinario();

    @Test
    public void acessarModuloBiblia(){
        acessarBiblia();
        marcarCapituloComoLido();
        voltarTelaInicial();
        acessarBiblia();
        voltarTelaInicial();
        hinario.abrirHinarioComSucesso();
    }
}
