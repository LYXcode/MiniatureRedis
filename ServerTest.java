import java.io.IOException;

import Config.ServerConfig;
import Policies.Persistence;
import Policies.RDBPersistence;
import Policies.RedisPolicy;
import Protocol.ProtocolHandler;
import jdk.jfr.Percentage;

public class ServerTest {
   public static void main(String[] args) throws IOException {
       System.out.println("HELLO, WELCOM TO MINIATURE REDIS");
       ServerConfig config= new ServerConfig("127.0.0.1", 4999, 10, 10, 20, 1L, 10);       
       Server server = new Server(config);

       RDBPersistence persistencePolicy = new RDBPersistence();
       persistencePolicy.setTime(10L);
       server.specifyPersistencePolicy(persistencePolicy);

       
       server.run();
   } 
}
