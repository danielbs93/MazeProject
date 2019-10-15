package Client;

/**
 * Created by Daniel Ben Simon
 */

import java.io.*;

public interface IClientStrategy {
    void clientStrategy(InputStream inFromServer, OutputStream outToServer);
}