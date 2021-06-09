import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import CommandHandler.ExpireHandler;
import Config.ServerConfig;
import Policies.ExpirePolicy;
import Policies.Persistence;
import Policies.PersistenceFactory;
import Policies.Policy;
import Policies.RDBPersistence;
import Policies.RedisPolicy;
import Protocol.ProtocolHandler;
import Storage.Storage;

public class Server {

    public volatile boolean running = true;

    private String host;
    private int port;
    private int maxClients;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private int capacity;

    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPool;

    private Persistence persistencePolicy = null;
    private boolean recoverFromBackup = false;
    private String recoverPath = "./DataBackup/persistence.out";

    private ExpirePolicy expirePolicy = null;

    public Server(ServerConfig serverConfig) throws IOException {
        this.host = serverConfig.getHost();
        this.port = serverConfig.getPort();
        this.maxClients = serverConfig.getMaxClients();
        this.corePoolSize = serverConfig.getCorePoolSize();
        this.maximumPoolSize = serverConfig.getMaximumPoolSize();
        this.keepAliveTime = serverConfig.getKeepAliveTime();
        this.capacity = serverConfig.getCapacity();

        serverSocket = new ServerSocket(this.port);
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity));

    }

    public void isRecoverDataFromBackup(boolean recoverFromBackup) {
        this.recoverFromBackup = recoverFromBackup;

    }

    public void specifyRecoverPath(String recoverPath) {
        this.recoverPath = recoverPath;
    }

    public void specifyPersistencePolicy(Persistence persistencePolicy) {
        this.persistencePolicy = persistencePolicy;
    }

    public void specifyExpirePolicy(ExpirePolicy expirePolicy){
        this.expirePolicy = expirePolicy;
    }

    public void run() {
        System.out.println("server running......");
        if (recoverFromBackup) {
            Storage.recoverData(recoverPath);
        }
        Storage.getStorage();
        if (persistencePolicy != null) {
            persistencePolicy.setFile(recoverPath);

            Thread persistenceThread = new Thread(persistencePolicy, "persistence thread");
            persistenceThread.start();
        }

        if(expirePolicy != null){
            Thread expireThread = new Thread(expirePolicy, "expire thread");
            expireThread.start();
        }

        while (running) {
            try {
                Socket socket = serverSocket.accept();

                ProtocolHandler protocolHandler = new ProtocolHandler(socket);
                threadPool.execute(protocolHandler);

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        persistencePolicy.running = this.running;
        expirePolicy.running = this.running;
    }
}
