package br.gov.go.agr.communicator.cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente {

    private int porta;
    private String host;
    private String mensagem;
    private Socket conexao;
    private BufferedReader leitorDeMensagem;
    private CaixaDeEntrada caixaDeEntrada;

    public Cliente() {
        this.host = "localhost";
        this.porta = 8000;
        this.caixaDeEntrada = new CaixaDeEntrada(this);
    }

    public Cliente(String host, int porta) {
        this.host = host;
        this.porta = porta;
        this.caixaDeEntrada = new CaixaDeEntrada(this);
    }


    public int getPorta() {
        return porta;
    }

    public void setPorta(int porta) {
        this.porta = porta;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Socket getConexao() {
        return conexao;
    }

    public BufferedReader getLeitorDeMensagem() {
        return leitorDeMensagem;
    }

    public CaixaDeEntrada getCaixaDeEntrada() {
        return caixaDeEntrada;
    }

    public void conectar() {
        try {
            conexao = new Socket(host, porta);
            System.out.println("\nConectando-se ao AGR Communicator Server.\n");
            leitorDeMensagem = new BufferedReader(
                new InputStreamReader(
                    conexao.getInputStream()
                )
            );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void desconectar() {
        if (conexao != null) {
            try {
                leitorDeMensagem.close();
                conexao.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void iniciarCaixaDeEntrada() {
        CaixaDeEntrada caixaDeEntrada = this.getCaixaDeEntrada();
        if (caixaDeEntrada != null) {
            caixaDeEntrada.start();
        }
    }

    public static void main(String[] args) {
        Cliente cliente = new Cliente();
        cliente.conectar();
        cliente.iniciarCaixaDeEntrada();
    } 
}