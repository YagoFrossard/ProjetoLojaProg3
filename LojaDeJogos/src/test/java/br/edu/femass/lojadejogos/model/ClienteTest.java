package br.edu.femass.lojadejogos.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ClienteTest {

    Jogo jogo = new Jogo();
    Cliente cliente = new Cliente();

    @Test
    void clienteDeterminarIdade(){
        Integer idade = 21;
        Integer nascimento = LocalDate.now().getYear() - idade;

        cliente.setAnoNascimento(nascimento);
        Assertions.assertEquals(idade, cliente.determinarIdade());
    }

    @Test
    void clientePodeComprar(){
        jogo.setClassificacao(Classificacao.A);
        cliente.setAnoNascimento(LocalDate.now().getYear() - 22);

        Assertions.assertTrue(cliente.podeComprar(jogo));
    }

    @Test
    void clienteNaoPodeComprar(){
        jogo.setClassificacao(Classificacao.M);
        cliente.setAnoNascimento(LocalDate.now().getYear() - 13);

        Assertions.assertFalse(cliente.podeComprar(jogo));
    }
}
