package client.vue;

import client.ctrl.ControleurClient;

public class Vue {

    private ControleurClient ctrl;

    public Vue(ControleurClient ctrl) {
        this.ctrl = ctrl;
    }

    public void menuInitial() {
        System.out.println("\nBONJOUR.\n");
        System.out.println("Client de la banque : tapez 1");
        System.out.println("Nouveau client : tapez 2");
        System.out.println("Fin programme : tapez 3\n");
        ctrl.choixInitial(MaConsole.readInt("Votre choix : "));
    }

    public void menuClient() {
        System.out.println("\nDepot : tapez 1");
        System.out.println("Retrait : tapez 2");
        System.out.println("Solde :  3");
        System.out.println("Fermer le compte : 4");
        System.out.println("Quitter : tapez 5\n");
        ctrl.choixClient(MaConsole.readInt("Votre choix : "));
    }

    public String demNom() {
        return (MaConsole.readLine("Entrez votre nom : "));
    }

    public Integer demMontant() {
        return (MaConsole.readInt("Entrez votre montant : "));
    }

    public void solde(Integer solde) {
        System.out.println("Votre solde est de : " + solde);
    }

    public void aff(String msg) {
        System.out.println(msg);
    }
}
	

