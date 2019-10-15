package Server;

/**
 * Created by Daniel Ben Simon
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private int port;
    private int listeningIntervalMS;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPoolExecutor;
    AtomicInteger ThreadCounter;
    private static final Logger LOG = LogManager.getLogger("log4j2.xml");


    public Server(int port, int listeningIntervalMS, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningIntervalMS = listeningIntervalMS;
        this.serverStrategy = serverStrategy;
    }



    public void start() {
        new Thread(() -> {
            runServer();
        }).start();
    }

    private void runServer() {
        try {
            Configurations Config = null;
            try{ Config = new Configurations(); }
            catch (Exception e){ e.getCause(); }
            threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(Integer.parseInt(Config.getNumOfThreads()));
            ThreadCounter = new AtomicInteger(Integer.parseInt(Config.getNumOfThreads()));
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(listeningIntervalMS);
            LOG.info(String.format("Server starter at %s!", serverSocket));
            LOG.info(String.format("Server's Strategy: %s", serverStrategy.getClass().getSimpleName()));
            LOG.info("Server is waiting for clients...");
            while (!stop) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    LOG.info(String.format("Client excepted: %s", clientSocket));
                    System.out.println();
                    threadPoolExecutor.execute(() -> {
                        handleClient(clientSocket);
                        LOG.info(String.format("Finished handle client: %s", clientSocket));
                    });
                } catch (SocketTimeoutException e) {
                    //e.printStackTrace();
                    //LOG.info("Socket Timeout - No clients pending!");
                }

            }
            threadPoolExecutor.shutdown();
            threadPoolExecutor.awaitTermination(5, TimeUnit.MINUTES);
            serverSocket.close();
        } catch (IOException e) {
            LOG.error("IOException - Error handing client!", e);
            //e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void handleClient(Socket clientSocket) {
        try {
            LOG.info(String.format("Handling client with socket: %s", clientSocket.toString()));
            serverStrategy.serverStrategy(clientSocket.getInputStream(), clientSocket.getOutputStream());
            ThreadCounter.decrementAndGet();
            while (ThreadCounter.get() != 0);
            clientSocket.close();


        } catch (IOException e) {
            LOG.info("IOException");
            e.printStackTrace();
        }
    }

    public void stop() {
        LOG.info("Stopping server..");
        stop = true;
    }

}
