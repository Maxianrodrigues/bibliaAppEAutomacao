package devandroid.muller.steps;

import org.junit.Test;

import devandroid.muller.pageObjects.AbrirHinario;

public class pesquisarHinosTest extends AbrirHinario {
    @Test
    public void testePesquisaHinoPorCodigoValido(){
        buscaPorCodigoHino("343");
    }
}
