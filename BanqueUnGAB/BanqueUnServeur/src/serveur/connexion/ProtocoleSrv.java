/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur.connexion;

import commun.Protocole;
import commun.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Administrateur
 */
public class ProtocoleSrv extends Protocole {

    public ProtocoleSrv(Socket s) throws IOException {
        socket = s;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }

    private int connexionBanque() {
        try {
            String msg = in.readLine();
            if (msg.equals("RQST")) {
                write("ACK");
                return CONN_ACK;
            } else {
                return CONN_NOT_BANK;
            }
        } catch (Exception e) {
            return CONN_KO;
        }
    }

    private Message attendreMsg() {
        try {
            msg_recu = new Message(in.readLine());
            write("ACK");
            return msg_recu;
        } catch (Exception e) {
            return null;
        }
    }

    public Message wait_message() {
        Message msg = null;
        if (connexionBanque() == CONN_ACK) {
            msg = attendreMsg();
        }
        return msg;
    }

    public int respond(Message reponse) {
        write(reponse);
        try {
            if (in.readLine().equals("ACK")) {
                return Message.M_OK;
            } else {
                return Message.M_KO;
            }
        } catch (Exception e) {
            return CONN_KO;
        }
    }
}
