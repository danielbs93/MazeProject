package Client;

/**
 * Created by Daniel Ben Simon.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

public class Client implements Serializable {
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;
    private static final Logger LOG = LogManager.getLogger("log4j2.xml");

    public Client(InetAddress IP, int port, IClientStrategy clientStrategy) {
        this.serverIP = IP;
        this.serverPort = port;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer(){
        try {
            Socket theServer = new Socket(serverIP, serverPort);
//            theServer.setSoTimeout(1000);
            LOG.info("Client is connected to server!");
            clientStrategy.clientStrategy(theServer.getInputStream(),theServer.getOutputStream());
                theServer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
