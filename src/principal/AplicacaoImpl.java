package principal;

import bd.Conexao2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;


/*
 import com.mysql.jdbc.Connection;
 import com.mysql.jdbc.PreparedStatement;
 import com.mysql.jdbc.Statement;
 */
public class AplicacaoImpl extends UnicastRemoteObject implements StubInterface {

    public Connection con = null;
    public PreparedStatement ptm = null;
    public Statement st = null;
    public ResultSet rs = null;
    /**
     *
     */

    private static final long serialVersionUID = 1L;

    protected AplicacaoImpl() throws RemoteException {
        super();
    }

    @Override
    public String prompt(String aplicativo) throws RemoteException {
        String comando = null;

        String[] cmds = {
            comando = "taskkill /f /im " + aplicativo + ".exe"
        };

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd", "/c",
                    String.join("& ", cmds));

            builder.redirectErrorStream(true);

            Process p = builder.start();

            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;

            while (true) {
                line = r.readLine();
                if (line == null) {
                    return null;
                }

                return line;
            }
        } catch (Exception e) {
            return "Erro " + e;
        }
    }

    @Override
    public String dadosServer() throws RemoteException {
        return "\nDados do Servidor: \n" + LocateRegistry.getRegistry();
    }

    @Override
    public int interManip(int bound) throws RemoteException {
        return bound + 20;
    }

    @Override
    public String dadosBD() throws RemoteException {
        con = Conexao2.getConexaoMySQL();
        String sql = "Select * from métodos_de_pagamentos_ref";

        ArrayList<String> lista = new ArrayList<String>();
        try {
            ptm = con.prepareStatement(sql);

            //ptm.setInt(1, id);
            rs = ptm.executeQuery();

            while (rs.next()) {
                lista.add("\nID:" + rs.getInt("CODIGO_METODO"));
                lista.add("Nome:" + rs.getString("NOME_METODO") + "\n");
            }
            // Gson g = new Gson();
            return "Bandeiras Aceitas:\n " + lista; //g.toJson(lista);

        } catch (SQLException ex) {
            return "Erro " + sql;
        }
    }

    @Override
    public void update() throws RemoteException {
        con = Conexao2.getConexaoMySQL();
        String sql = " UPDATE métodos_de_pagamentos_ref SET nome_metodo='Visa' WHERE codigo_metodo = '0'";
        
        try {
            st = con.createStatement();
            //ptm = con.prepareStatement(sql);
            
            int up = st.executeUpdate(sql);
            
            if(up == 1)
                JOptionPane.showMessageDialog(null, "Alterado");
            else
                JOptionPane.showMessageDialog(null, "Não");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Inalterado, ERRO: "+ex);
        }
    }

    /*
    @Override
    public String email(String assunto, String mensagem, final String email, final String senha, String destino) throws RemoteException {
        Properties props = new Properties();
        /**
         * Parâmetros de conexão com servidor Gmail
     */
 /*
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, senha);
            }
        });

        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email)); //Remetente

            Address[] toUser = InternetAddress //Destinatário(s)
                    .parse(destino);

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject(assunto);//Assunto
            message.setText(mensagem);
            /**
             * Método para enviar a mensagem criada
     */
 /*
            Transport.send(message);

            return "Email enviado com Sucesso!";

        } catch (MessagingException e) {
            return "Não foi possível enviar o Email!";
        }

    }*/
}
