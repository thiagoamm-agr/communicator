package br.gov.go.agr.communicator.servidor;

import java.io.IOException;
import java.io.PrintStream;

import java.net.ServerSocket;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Servidor {

    private static String prompt = "[COMUNICAR]   >";
    private static String promptNotificacao = "[NOTIFICACAO] >";
    private static String promptCliente = "[COMUNICADO]  >";
    private static Teclado teclado;
    private static List<Conexao> destinatarios = new ArrayList<>();

    public static void main(String[] args) {
        ServerSocket servidor = null;
        try {
            servidor = new ServerSocket(8000);
            System.out.println("\n\t\t\t[AGR Communicator Server]\n");
            Servidor.mostrarData();
            teclado = new Teclado();
            teclado.start();
            while (true) {
                Thread.sleep(1000);
                Conexao conexao = new Conexao(servidor);
                conexao.start();
                destinatarios.add(conexao);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (servidor != null) {
                    servidor.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void comunicar(String prefixo, String mensagem, Conexao destinatario) {
        if (destinatario != null) {
            destinatario.getSaida().printf("%s %s %s\n", Servidor.getHora(), prefixo, mensagem);
            destinatario.getSaida().flush();
        }
    }

    public static void comunicar(String mensagem, Conexao destinatario) {
        Servidor.comunicar(promptCliente, mensagem, destinatario);
    }

    public static void comunicar(String mensagem) {
        for (Conexao destinatario : destinatarios) {
            comunicar(mensagem, destinatario);
        }
    }

    public static String getPrompt() {
        return String.format("%s %s ", Servidor.getHora(), prompt);
    }

    public static void notificar(String notificacao, PrintStream destinatario) {
        if (destinatario != null) {
            String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
            destinatario.printf(
                "\n\n%s %s %s\n\n",
                hora,
                promptNotificacao,
                notificacao
            );
        }
    }
    
    public static void notificar(String notificacao) {
        Servidor.notificar(notificacao, System.out);
    }

    public static void mostrarData(String mensagem, PrintStream destinatario) {
        if (destinatario != null) {
            String data = new SimpleDateFormat("EEEE, dd/MM/yyyy HH:mm:ss").format(new Date());
            destinatario.printf(mensagem, data);
        }
    }

    public static void mostrarData() {
        Servidor.mostrarData("Data e hora do Servidor: %s\n\n", System.out);
    }

    public static String getHora() {
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        return hora;
    }

    public static void sair() {
        System.exit(0);
    }
}
