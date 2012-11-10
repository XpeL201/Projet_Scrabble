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
public interface IBanque {

    double depot(String nom, double montant) throws ExceptionBanque;

    void fermerCompte(String nom) throws ExceptionBanque;

    void ouvrirCompte(String nom) throws ExceptionBanque;

    double retrait(String nom, double montant) throws ExceptionBanque;

    double solde(String nom) throws ExceptionBanque;

    void deconnexion(String nom) throws ExceptionBanque;

}
