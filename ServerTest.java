import java.io.IOException;

public class ServerTest {
   public static void main(String[] args) throws IOException {
       System.out.println("HELLO, WELCOM TO MINIATURE REDIS");
       ServerConfig config= new ServerConfig("127.0.0.1", 4999, 10, 10, 20, 1L, 10);
       ProtocolHandler protocolHandler = new ProtocolHandler();
       Server server = new Server(config, protocolHandler);
       server.run();
   } 
}
