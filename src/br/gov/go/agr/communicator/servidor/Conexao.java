package br.gov.go.agr.communicator.servidor;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Conexao extends Thread {

    private ServerSocket servidor;
    private Socket cliente;
    private PrintStream saida;

    public Conexao(ServerSocket servidor) {
        try {
            cliente = servidor.accept();
            this.saida = new PrintStream(cliente.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.servidor = servidor;
    }

    public ServerSocket getServidor() {
        return servidor;
    }

    public void setServidor(ServerSocket servidor) {
        this.servidor = servidor;
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public PrintStream getSaida() {
        return saida;
    }

    public void run() {
        Servidor.notificar(
            String.format(
                "agente %s conectado\n", 
                cliente.getInetAddress().getHostAddress()
            )
        );
        System.out.print(Servidor.getPrompt());
        Servidor.mostrarData("Data e hora do Servidor: %s", this.saida);
        Servidor.notificar("Conexão efetuada com sucesso.", this.saida);
    }
}
