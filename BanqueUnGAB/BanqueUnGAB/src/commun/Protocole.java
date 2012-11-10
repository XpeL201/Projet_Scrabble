package commun;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class Protocole {

    protected Socket socket;
    protected BufferedReader in;
    protected PrintWriter out;
    protected Message msg_recu = null;
    protected String adrIP;
    protected int port;
    
    //Messages de connexions
    public static final int CONN_OK = 100;
    public static final int CONN_KO = 200;
    public static final int CONN_NOT_INIT = 300;
    public static final int CONN_NOT_BANK = 400; 
    public static final int CONN_ACK = 500;
    

    protected void write(String s) {
        out.println(s);
        out.flush();
    }

    protected void write(Message m) {
        out.println(m.toString());
        out.flush();
    }
}
