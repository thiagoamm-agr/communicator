package br.gov.go.agr.communicator.cliente;

import java.io.BufferedReader;
import java.io.IOException;

public class CaixaDeEntrada extends Thread {

    private Cliente cliente;

    public CaixaDeEntrada(Cliente cliente) {
        this.cliente = cliente;
    }

    public void run() {
        if (cliente != null) {
            BufferedReader leitorMensagem = cliente.getLeitorDeMensagem();
            if (leitorMensagem != null) {
                try {
                    while (true) {
                        String mensagem = leitorMensagem.readLine();
                        if (mensagem != null) {
                            System.out.println(mensagem);
                            cliente.setMensagem(null);
                            if (mensagem.contains(" [COMUNICADO]  > ")) {
                                int posicao = mensagem.indexOf(">") + 2;
                                mensagem = mensagem.substring(posicao, mensagem.length());
                                cliente.setMensagem(mensagem);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        leitorMensagem.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
