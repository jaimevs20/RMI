package principal;


import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Jaiminho
 */
public class Server {

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "localhost");
            LocateRegistry.createRegistry(1099);

            StubInterface c = new AplicacaoImpl();
            Naming.rebind("AppService", (Remote) c);
            System.out.println("\nServidor - rodando...");
        } catch (Exception e) {
            System.out.println("Erro " + e);
        }
    }

}