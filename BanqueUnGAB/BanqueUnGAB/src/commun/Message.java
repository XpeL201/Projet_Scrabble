package commun;

public class Message {

    private Integer code = 0;
    private String nom = "";
    private Integer montant = 0;
    private String paquet = "";
    
    
    //Messages de retour
    public static final int M_OK = 1; //Pas de probleme
    public static final int M_KO = 2; //KO raion inconnue
    public static final int M_CLIEXIST = 3; //KO : Le client existe
    public static final int M_CLINOTEXIST = 4; //KO : Le client n'existe pas
    public static final int M_SOLDEINS = 5; //KO : Le solde est insuffisant
    public static final int M_NONAUTORISE = 6; //Opération non autorisée
    //Messages d'opérations
    public static final int OP_I = 10; //Inscription
    public static final int OP_U = 20; //Dés-inscription
    public static final int OP_L = 30; //Login
    public static final int OP_D = 40; //Dépot
    public static final int OP_R = 50; //Retrait
    public static final int OP_S = 60; //Solde
    public static final int OP_DEC = 70; //Déconnexion

    public Message(Integer code, String nom, Integer montant) {
        this.code = code;
        this.nom = nom;
        this.montant = montant;
        setPaquet();
    }

    public Message(String msg) {
        String[] str = new String[3];
        str = msg.split(":");
        this.code = Integer.parseInt(str[0]);
        this.nom = str[1];
        this.montant = Integer.parseInt(str[2]);
        setPaquet();
    }

    public Integer getCode() {
        return code;
    }

    public String getNom() {
        return nom;
    }

    public Integer getMontant() {
        return montant;
    }

    @Override
    public String toString() {
        return paquet;
    }

    private void setPaquet() {
        paquet = code.toString() + ":" + nom + ":" + montant.toString();
    }
}
