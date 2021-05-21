import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {

    private String host;
    private int port;
    private int maxClients;
    private int corePoolSize;
    private int maximumPoolSize;
    private long keepAliveTime;
    private int capacity;

    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPool;
    private AbstractProtocolHandler protocolHandler;

    public Server(ServerConfig serverConfig, AbstractProtocolHandler protocolHandler) {
        this.host = serverConfig.getHost();
        this.port = serverConfig.getPort();
        this.maxClients = serverConfig.getMaxClients();
        this.corePoolSize = serverConfig.getCorePoolSize();
        this.maximumPoolSize = serverConfig.getMaximumPoolSize();
        this.keepAliveTime = serverConfig.getKeepAliveTime();
        this.capacity = serverConfig.getCapacity();
        this.protocolHandler = protocolHandler;

        serverSocket = new ServerSocket(this.port);
        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity));

    }
}
