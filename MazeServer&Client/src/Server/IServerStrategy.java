package Server;

/**
 * Created by Daniel Ben Simon
 */

import java.io.*;

public interface IServerStrategy {
    void serverStrategy(InputStream inFromClient, OutputStream outToClient);
}
