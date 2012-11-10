/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur.model;

import commun.ExceptionBanque;

/**
 *
 * @author Administrateur
 */
public abstract class Banque implements IBanque {

        //************* Opérations fonctionnelles ************************
    @Override
    public void ouvrirCompte(String nom) throws ExceptionBanque {
        if (! addClient(nom)) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.CLIENT_EXISTE);
        }
    }
    
    @Override
    public void fermerCompte(String nom) throws ExceptionBanque {
        if (!removeClient(nom)) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.CLIENT_EXISTE_PAS);
        }
    }

    @Override
    public double depot(String nom, double montant) throws ExceptionBanque {
        Client client = chercherClient(nom);
        if (client == null) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.CLIENT_EXISTE_PAS);
        }
        if (montant < 0) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.MONTANT_NEGATIF);
        }
        client.compte().depot(montant);
        updateClient(client);
        return client.compte().solde();
    }

    @Override
    public double retrait(String nom, double montant) throws ExceptionBanque {
        Client client = chercherClient(nom);
        if (client == null) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.CLIENT_EXISTE_PAS);
        }
        if (montant < 0) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.MONTANT_NEGATIF);
        }
        Compte compte = client.compte();
        if (!compte.retrait(montant)) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.SOLDE_INSUFFISANT);
        }
        else
            updateClient(client);
        return compte.solde();
    }

    @Override
    public double solde(String nom) throws ExceptionBanque {
        Client client = chercherClient(nom);
        if (client == null) {
            throw new ExceptionBanque(ExceptionBanque.typeErreur.CLIENT_EXISTE_PAS);
        }
        Compte compte = client.compte();
        return compte.solde();
    }

    //Déconnexion - Utile pour les opérations de mise à jour différées
    @Override
    public void deconnexion(String nom) throws ExceptionBanque {}

    //updateClient pour les DB - ne fait rien pour banqueRAM

    protected void updateClient(Client client) {};

    //*********************  Méthodes abstraites *********************
    protected abstract Client chercherClient(String nom);
    protected abstract boolean addClient(String nom);
    protected abstract boolean removeClient(String nom);
    


}
