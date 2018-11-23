package principal;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jaiminho
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StubInterface extends Remote {

    public String prompt(String comando) throws RemoteException;
    public String dadosServer() throws RemoteException;
    public int interManip(int bound) throws RemoteException;
    public String dadosBD() throws RemoteException;
    public void update() throws RemoteException;
  //  public String email(String assunto,String mensagem,String email, String senha, String destino);

}
