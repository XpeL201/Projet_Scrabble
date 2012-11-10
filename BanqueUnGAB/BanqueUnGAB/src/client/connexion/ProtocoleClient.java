/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.connexion;

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
public class ProtocoleClient extends Protocole {

    public ProtocoleClient(String adrIP, int port) {
        this.adrIP = adrIP;
        this.port = port;
    }
    

    public Message send_message(int code, String nom, int montant) {
        Message msgRep = null;
        if (connexionTCP() == CONN_OK) {
            int etat = connexionBanque();
            if (etat == CONN_ACK) {
                etat = envoiMsg(new Message(code, nom, montant));
                if (etat == CONN_ACK){
                    msgRep = attenteReponse();
                }
            }
            if (etat != CONN_KO)
                deconnexionTCP();
         }
        return msgRep;
    }

    private int connexionTCP() {
        try {
            socket = new Socket(adrIP, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            return CONN_OK;
        } catch (Exception e) {
            return CONN_KO;
        }
    }

    private int connexionBanque() {
        try {
            write("RQST");
            if (in.readLine().equals("ACK")) {
                return CONN_ACK;
            } else {
                return CONN_NOT_BANK;
            }
        } catch (IOException ex) {
            return CONN_KO;
        }
    }

    private int envoiMsg(Message msgToSend) {
        write(msgToSend);
        try {
            if (in.readLine().equals("ACK")) {
                return CONN_ACK;
            } else {
                return CONN_NOT_BANK;
            }
        } catch (IOException ex) {
            return CONN_KO;
        }
    }
    
    private Message attenteReponse() {
        try {
            msg_recu = new Message(in.readLine());
            write("ACK");
            return msg_recu;
        } catch (Exception e) {
            return null;
        }
    }

    private int deconnexionTCP() {
        try {
            socket.close();
            return CONN_OK;
        } catch (Exception e) {
            return CONN_KO;
        }
    }
}
