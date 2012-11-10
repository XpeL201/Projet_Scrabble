/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur.model;

/**
 *
 * @author Administrateur
 */
public class Client {

    private String nom;
    private int idClient;

    public Client(String nom) {
        this.nom = nom;
        compte = new Compte();
    }

    public Compte compte() {
        return compte;
    }

    public void setCompte(Compte compte) {
        this.compte = compte;
    }

    public String nom() {
        return nom;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    private Compte compte = null;
}
