/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur.model;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Administrateur
 */
public class BanqueRAM extends Banque{

    private Map<String, Client> clients = new HashMap<String, Client>();
    
    //*********************  Méthodes abstraites implémentées *********************
    @Override
    protected Client chercherClient(String nom) {
        return clients.get(nom);
    }
    

    @Override
    protected boolean addClient(String nom) {
        if (clients.containsKey(nom)) {
            return false;
        }
        Client client = new Client(nom);
        clients.put(nom, client);
        return true;
    }

    @Override
    protected boolean removeClient(String nom) {
        return (clients.remove(nom) != null);
    }

}
