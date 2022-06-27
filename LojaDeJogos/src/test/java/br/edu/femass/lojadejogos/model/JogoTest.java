package br.edu.femass.lojadejogos.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JogoTest {

    Jogo jogo = new Jogo();

    @Test
    void jogoPodeVender(){
        jogo.setUnidEstoque(5);

        Assertions.assertTrue(jogo.podeVender(4));
    }

    @Test
    void jogoNaoPodeVender(){
        jogo.setUnidEstoque(5);

        Assertions.assertFalse(jogo.podeVender(6));
    }

    @Test
    void comprarJogo(){
        Integer quantidade = 5;
        jogo.setUnidEstoque(10);
        jogo.comprar(quantidade);

        Assertions.assertEquals(15, jogo.getUnidEstoque());
    }

    @Test
    void comprarJogoErro1(){
        Integer quantidade = 4;
        jogo.setUnidEstoque(10);
        jogo.comprar(quantidade);

        Assertions.assertNotEquals(15, jogo.getUnidEstoque());
    }

    @Test
    void comprarJogoErro2(){
        Integer quantidade = 6;
        jogo.setUnidEstoque(10);
        jogo.comprar(quantidade);

        Assertions.assertNotEquals(15, jogo.getUnidEstoque());
    }

    @Test
    void venderJogo(){
        Integer quantidade = 10;
        jogo.setUnidEstoque(20);
        jogo.vender(quantidade);

        Assertions.assertEquals(10, jogo.getUnidEstoque());
    }

    @Test
    void venderJogoErro1(){
        Integer quantidade = 10;
        jogo.setUnidEstoque(20);
        jogo.vender(quantidade);

        Assertions.assertNotEquals(9, jogo.getUnidEstoque());
    }

    @Test
    void venderJogoErro2(){
        Integer quantidade = 10;
        jogo.setUnidEstoque(20);
        jogo.vender(quantidade);

        Assertions.assertNotEquals(11, jogo.getUnidEstoque());
    }
}
