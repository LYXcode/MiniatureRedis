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


            // bufferedWriter.write("hset hash0 name0 value0 name1 value1\r\n");
            bufferedWriter.write("hget hash0\r\n");
            // bufferedWriter.write("hdel hash0 name\r\n");


            // bufferedWriter.write("sadd set0 value0 value1 value3 value123456\r\n");
            // bufferedWriter.write("sadd set1 value3 value123456 valuehhhhh\r\n");
            // bufferedWriter.write("srem set0 value0\r\n");
            // bufferedWriter.write("smembers set0\r\n");
            // bufferedWriter.write("sunion set0 set1\r\n");
            // bufferedWriter.write("sismember set0 value12\r\n");
            // bufferedWriter.write("srandmembers set0 3\r\n");
            // bufferedWriter.write("spop set0 2\r\n");




            // bufferedWriter.write("rpush list0 value0 value1 value3 value123456\r\n");
            // bufferedWriter.write("rrange list0 2 5\r\n");
            // bufferedWriter.write("rpush list1  value123 value987\r\n");
            // bufferedWriter.write("rrange list0\r\n");
            // bufferedWriter.write("expire list0 20\r\n");
            // bufferedWriter.write("ttl list0\r\n");
            // bufferedWriter.write("llen list0 list1\r\n");
            // bufferedWriter.write("rpop list0 \r\n");
            // bufferedWriter.write("lpop list0 \r\n");
            // bufferedWriter.write("get list0\r\n");
            


            // bufferedWriter.write("set key0 10 key1 11sda key2 dsa expire 30\r\n");
            // bufferedWriter.write("get key0 key1 key2\r\n");
            // bufferedWriter.write("del key0\r\n");
            // bufferedWriter.write("strlen key0 key1 key2\r\n");
            // bufferedWriter.write("decr key0 key1 key2\r\n");
            //  bufferedWriter.write("exists key0 key1 key2 key3\r\n");
            // bufferedWriter.write("expire key0 9\r\n");
            // bufferedWriter.write("ttl key0  key1  key2 key5 key6\r\n");
            // bufferedWriter.write("persist key0\r\n");

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
