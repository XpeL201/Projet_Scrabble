package client.ctrl;

import commun.ExceptionBanque;
import client.model.GAB;
import client.vue.Vue;
//import utils.Util;

public class ControleurClient {

    private Vue vue;
    private GAB gab;
    private static String adrIP = "localhost";
    private static int port = 8189;

    public static void main(String[] args) {
        ControleurClient ctrlClient = new ControleurClient(args);
        ctrlClient.affInit();
    }
    
    //***************  Lancement du pgm client ***********************
    public ControleurClient(String[] args) {
        switch (args.length) {
            case 1:
                adrIP = args[0];
                break;
            case 2:
                adrIP = args[0];
                port = Integer.valueOf(args[1].trim()).intValue();
                break;
        }
        vue = new Vue(this);
        gab = new GAB(adrIP, port);
    }
    
    public void affInit() {
        vue.menuInitial();
    }

    public void choixInitial(Integer choix) {
        switch (choix) {
            case 1:
                clientBanque();
                break;
            case 2:
                String nomClient = vue.demNom();
                try {
                    gab.ouvrirCompte(nomClient);
                    vue.aff("Bienvenue dans notre banque " + nomClient);
                    vue.menuClient();
                } catch (ExceptionBanque e) {
                    traiter_exception(e);
                }
                break;
            case 3:
                vue.aff("Au revoir et merci !");
                break;
            default:
                vue.aff("Mauvais choix");
                vue.menuInitial();
                break;
        }
    }

    public void choixClient(Integer choix) {
        try {
            switch (choix) {
                case 1:
                    gab.depot(demMontant());
                    vue.aff("Opération menée à bien. Votre nouveau solde est de " + gab.getSolde());
                    vue.menuClient();
                    break;
                case 2:
                    gab.retrait(demMontant());
                    vue.aff("Opération menée à bien. Votre nouveau solde est de " + gab.getSolde());
                    vue.menuClient();
                    break;
                case 3:
                    gab.solde();
                    vue.aff("Votre solde est de " + gab.getSolde());
                    vue.menuClient();
                    break;
                case 4:
                    gab.fermerCompte();
                    vue.aff("C'est bien triste que vous nous quittez, " + gab.getNomClient() + " !");
                    vue.menuInitial();
                    break;
                case 5:
                    gab.deconnexion();
                    vue.menuInitial();
                    break;
            }
        } catch (ExceptionBanque e) {
            traiter_exception(e);
        }
    }

    //********************* Méthodes privées ************************

    private void traiter_exception(ExceptionBanque e) {
        switch (e.getErreur()) {
            case CLIENT_EXISTE:
                vue.aff("Cher " + gab.getNomClient() + ", vous êtes déjà client de notre banque");
                vue.menuClient();
                break;
            case CLIENT_EXISTE_PAS:
                vue.aff("Cher " + gab.getNomClient() + ", vous n'êtes pas client de notre banque !");
                vue.menuInitial();
                break;
            case SOLDE_INSUFFISANT:
                vue.aff("Désolé, votre solde est insuffisant. Il vous reste " + gab.getSolde() + " sur votre compte");
                vue.menuClient();
                break;
            case CONN_KO:
                vue.aff("Connexion avec la banque impossible actuellement. Désolé !");
                vue.menuClient();
                break;

            case AUTRE:
                vue.aff("Problème inconnu !");
                break;
        }
    }

    //***************     Lien avec la vue *****************************
    private void clientBanque() {
        gab.setNomClient(vue.demNom());
        vue.aff("Bienvenue " + gab.getNomClient() + "\n");
        vue.menuClient();
    }

    private Integer demMontant() {
        Integer montant = 0;
        boolean valide = false;
        while (!valide) {
            montant = vue.demMontant();
            if (montant <= 0) {
                vue.aff("Montant non valide");
            } else {
                valide = true;
            }
        }
        return montant;
    }
}
