/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

import client.connexion.ProtocoleClient;
import commun.ExceptionBanque;
import commun.Protocole;
import commun.Message;

/**
 *
 * @author Administrateur
 */
public class GAB {

    public GAB(String adrIP, int port) {
        protocole = new ProtocoleClient(adrIP, port);
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public void ouvrirCompte(String nom) throws ExceptionBanque {
        nomClient = nom;
        Message reponse = protocole.send_message(Message.OP_I, nom, 0);
        traiter_rep(reponse);
    }

    public void fermerCompte() throws ExceptionBanque {
        Message reponse = protocole.send_message(Message.OP_U, nomClient, 0);
        traiter_rep(reponse);
    }

    public void depot(int montant) throws ExceptionBanque {
        Message reponse = protocole.send_message(Message.OP_D, nomClient, montant);
        if (traiter_rep(reponse))
            solde = reponse.getMontant();
    }

    public void retrait(int montant) throws ExceptionBanque {
        Message reponse = protocole.send_message(Message.OP_R, nomClient, montant);
        if (traiter_rep(reponse))
            solde = reponse.getMontant();
    }

    public double solde() throws ExceptionBanque {
        Message reponse = protocole.send_message(Message.OP_S, nomClient, 0);
        if (traiter_rep(reponse))
            solde = reponse.getMontant();
        return solde;
    }

    public void deconnexion() throws ExceptionBanque {
        /*
        Message reponse = protocole.send_message(Message.OP_DEC, nomClient, 0);
        traiter_rep(reponse);
         * 
         */
    }

    public double getSolde() {
        return solde;
    }

    private boolean traiter_rep(Message reponse) throws ExceptionBanque {
        if (reponse == null) throw new ExceptionBanque(ExceptionBanque.typeErreur.CONN_KO);
        if (reponse.getCode() == Message.M_OK) return true;
        throw traiter_err(reponse.getCode());
    }

    private ExceptionBanque traiter_err(int err) {
        switch (err) {
            case Message.M_CLIEXIST:
                return new ExceptionBanque(ExceptionBanque.typeErreur.CLIENT_EXISTE);
            case Message.M_CLINOTEXIST:
                return new ExceptionBanque(ExceptionBanque.typeErreur.CLIENT_EXISTE_PAS);
            case Message.M_SOLDEINS:
                return new ExceptionBanque(ExceptionBanque.typeErreur.SOLDE_INSUFFISANT);
            case Protocole.CONN_KO:
                return new ExceptionBanque(ExceptionBanque.typeErreur.CONN_KO);
            default:
                return new ExceptionBanque(ExceptionBanque.typeErreur.AUTRE);
        }
    }
    
    private String nomClient;
    private double solde = 0;
    private ProtocoleClient protocole;
}
