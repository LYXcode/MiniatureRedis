import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 4999);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // bufferedWriter.write("set key0 10 key1 11sda key2 dsa expire 20\r\n");
            bufferedWriter.write("get key0 key1 key2\r\n");
            // bufferedWriter.write("del key0\r\n");
            // bufferedWriter.write("strlen key0 key1 key2\r\n");
            // bufferedWriter.write("decr key0 key1 key2\r\n");
            //  bufferedWriter.write("exists key0 key1 key2 key3\r\n");
            // bufferedWriter.write("expire key0 900\r\n");
            bufferedWriter.write("ttl key0  key1  key2 key5 \r\n");

            bufferedWriter.flush();


            String res = bufferedReader.readLine();

            System.out.println(res);
            bufferedWriter.close();
            // bufferedReader.close();

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
