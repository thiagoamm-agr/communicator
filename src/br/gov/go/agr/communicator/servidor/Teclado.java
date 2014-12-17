package br.gov.go.agr.communicator.servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Teclado extends Thread {

    private BufferedReader teclado;

    public Teclado() {
        teclado = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run() {
        String mensagem = "";
        try {
            while (!mensagem.trim().equals(":sair")) {
                System.out.print(Servidor.getPrompt());
                mensagem = teclado.readLine();
                Servidor.comunicar(mensagem);
            }
            Servidor.sair();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
