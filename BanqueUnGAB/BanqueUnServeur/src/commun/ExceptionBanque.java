/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package commun;

/**
 *
 * @author Administrateur
 */
public class ExceptionBanque extends Exception{

    public ExceptionBanque(typeErreur err) {
        this.err = err;
    }

    public typeErreur getErreur() {
        return err;
    }
    
    public enum typeErreur {CONN_KO,CLIENT_EXISTE, CLIENT_EXISTE_PAS, SOLDE_INSUFFISANT, MONTANT_NEGATIF,AUTRE};
    private typeErreur err;
}
