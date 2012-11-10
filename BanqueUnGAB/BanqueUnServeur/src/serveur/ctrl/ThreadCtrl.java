package serveur.ctrl;

import serveur.connexion.ProtocoleSrv;
import commun.Protocole;
import commun.Message;
import commun.ExceptionBanque;
import serveur.model.*;
import serveur.model.BanqueFactory;

class ThreadCtrl extends Thread {

    private IBanque banque;
    private ProtocoleSrv proto;
    Message demande;

    public ThreadCtrl(ProtocoleSrv p) {
        proto = p;
        banque = BanqueFactory.getBanque();
    }

    @Override
    public void run() {
        demande = proto.wait_message();
        traiter_msg();
    }

    private void traiter_msg() {
        if (demande == null) {
            aff("Problème de protocole");
            return;
        }
        switch (demande.getCode()) {
            case Message.OP_D:
                depot();
                break;
            case Message.OP_R:
                retrait();
                break;
            case Message.OP_S:
                solde();
                break;
            case Message.OP_U:
                supprClient();
                break;
            case Message.OP_I:
                ajoutClient();
                break;
            case Message.OP_DEC:
                deconnexionClient();
                break;
            case Protocole.CONN_KO:
                break;
            default:
                nonAutorise(demande);
        }
    }//fin traiterClient

    private void traiter_err(ExceptionBanque e) {
        Message reponse = null;
        switch (e.getErreur()) {
            case CLIENT_EXISTE:
                reponse = new Message(Message.M_CLIEXIST,"",0);
                break;
            case CLIENT_EXISTE_PAS:
                reponse = new Message(Message.M_CLINOTEXIST,"",0);
                break;
            case SOLDE_INSUFFISANT:
                reponse = new Message(Message.M_SOLDEINS,"",0);
                break;
            case CONN_KO:
                reponse = new Message(Protocole.CONN_KO,"",0);
                break;
            case AUTRE:
                reponse = new Message(Protocole.CONN_NOT_INIT,"",0);
                break;
        }
        proto.respond(reponse);
    }

    private void deconnexionClient() {
        try {
            String nomClient = demande.getNom();
            aff(nomClient + " se déconnecte");
            banque.deconnexion(nomClient);
            Message reponse = new Message(Message.M_OK, nomClient, 0);
            proto.respond(reponse);
        } catch (ExceptionBanque e) {
            traiter_err(e);
        }
    }

    private void depot() {
        String nomClient = demande.getNom();
        double montant = demande.getMontant();
        aff(nomClient + " fait un dépot");
        try {
            int new_solde = (int) banque.depot(nomClient, montant);
            Message reponse = new Message(Message.M_OK, nomClient, new_solde);
            proto.respond(reponse);
        } catch (ExceptionBanque e) {
            traiter_err(e);
        }
    }

    private void retrait() {
        String nomClient = demande.getNom();
        double montant = demande.getMontant();
        aff(nomClient + " fait un retrait");
        try {
            int new_solde = (int) banque.retrait(nomClient, montant);
            Message reponse = new Message(Message.M_OK, nomClient, new_solde);
            proto.respond(reponse);
        } catch (ExceptionBanque e) {
            traiter_err(e);
        }
    }

    private void solde() {
        String nomClient = demande.getNom();
        aff(nomClient + " demande son solde");
        try {
            int solde = (int)banque.solde(nomClient);
            Message reponse = new Message(Message.M_OK, nomClient, solde);
            proto.respond(reponse);
        } catch (ExceptionBanque e) {
            traiter_err(e);
        }
    }

    private void supprClient() {
        String nomClient = demande.getNom();
        aff(nomClient + " se dés-inscrit");
        try {
            banque.fermerCompte(nomClient);
            Message reponse = new Message(Message.M_OK, nomClient, 0);
            proto.respond(reponse);
        } catch (ExceptionBanque e) {
            traiter_err(e);
        }
    }

    private void ajoutClient() {
        String nomClient = demande.getNom();
        aff(nomClient + " s'inscrit");
        try {
            banque.ouvrirCompte(nomClient);
            Message reponse = new Message(Message.M_OK, nomClient, 0);
            proto.respond(reponse);
        } catch (ExceptionBanque e) {
            traiter_err(e);
        }
    }

    private void nonAutorise(Message demande) {
        String nomClient = demande.getNom();
        aff(nomClient + " fait une opération non autorisée : " + demande.getCode());
        Message reponse = new Message(Message.M_OK, nomClient, 0);
        proto.respond(reponse);
    }

    private void aff(String msg) {
        System.out.println("SERVEUR : "+msg);
    }
}//fin classe	

