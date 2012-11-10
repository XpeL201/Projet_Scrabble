/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur.model;

/**
 *
 * @author Administrateur
 */
public class Compte {

    private double solde = 0;

    public synchronized boolean retrait(double montant) {
        if (montant < 0) return false;
        if (montant > solde) return false;
        solde -= montant;
        return true;
    }

    public synchronized boolean depot(double montant) {
        if (montant < 0) return false;
        solde += montant;
        return true;
    }

    public synchronized double solde() {
        return solde;
    }

    public synchronized void setSolde(double solde) {
        this.solde = solde;
    }

}
