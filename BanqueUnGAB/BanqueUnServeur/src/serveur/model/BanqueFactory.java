/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package serveur.model;


/**
 *
 * @author Administrateur
 */
public class BanqueFactory {
    private static IBanque banque = null;

    public static synchronized IBanque getBanque() {
        if (banque == null) {
            banque = new BanqueRAM();
        }
        return banque;
    }


}
