import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

    private String ip;
    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String sendCommand(String command) throws IOException {
        Socket socket = new Socket(ip, port);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter.write(command + "\r\n");

        bufferedWriter.flush();

        String res = bufferedReader.readLine();

        System.out.println(res);
        bufferedWriter.close();
        bufferedReader.close();
        socket.close();

        return res;

    }

    // bufferedWriter.write("set key0 10 key1 11sda key2 dsa expire 30\r\n");
    // bufferedWriter.write("get key0 key1 key2\r\n");
    // bufferedWriter.write("del key0\r\n");
    // bufferedWriter.write("strlen key0 key1 key2\r\n");
    // bufferedWriter.write("decr key0 key1 key2\r\n");
    // bufferedWriter.write("exists key0 key1 key2 key3\r\n");
    // bufferedWriter.write("expire key0 9\r\n");
    // bufferedWriter.write("ttl key0 key1 key2 key5 key6\r\n");
    // bufferedWriter.write("persist key0\r\n");

    // bufferedWriter.write("rpush list0 value0 value1 value3 value123456\r\n");
    // bufferedWriter.write("rrange list0 2 5\r\n");
    // bufferedWriter.write("rpush list1 value123 value987\r\n");
    // bufferedWriter.write("rrange list0\r\n");
    // bufferedWriter.write("expire list0 20\r\n");
    // bufferedWriter.write("ttl list0\r\n");
    // bufferedWriter.write("llen list0 list1\r\n");
    // bufferedWriter.write("rpop list0 \r\n");
    // bufferedWriter.write("lpop list0 \r\n");
    // bufferedWriter.write("get list0\r\n");

    // bufferedWriter.write("sadd set0 value0 value1 value3 value123456\r\n");
    // bufferedWriter.write("sadd set1 value3 value123456 valuehhhhh\r\n");
    // bufferedWriter.write("srem set0 value0\r\n");
    // bufferedWriter.write("smembers set0\r\n");
    // bufferedWriter.write("sunion set0 set1\r\n");
    // bufferedWriter.write("sismember set0 value12\r\n");
    // bufferedWriter.write("srandmembers set0 3\r\n");
    // bufferedWriter.write("spop set0 2\r\n");

    // bufferedWriter.write("hset hash0 name0 value0 name1 value1\r\n");
    // bufferedWriter.write("hget hash0\r\n");
    // bufferedWriter.write("hdel hash0 name\r\n");

    public String hdel(String key, String name) throws IOException {

        String command = "hget " + key + " " + name + " ";

        return sendCommand(command);
    }

    public String hget(String key) throws IOException {

        String command = "hget " + key + " ";

        return sendCommand(command);
    }

    public String hset(String key, String[] names, String[] values) throws IOException {

        String command = "hset " + key + " ";

        if (names.length != values.length || names.length < 1) {
            return null;
        }

        for (int i = 0; i < names.length; i++) {
            command = command + names[i] + " " + values[i] + " ";
        }
        return sendCommand(command);
    }

    public String spop(String key, String num) throws IOException {

        String command = "spop " + key + " " + num + " ";

        return sendCommand(command);
    }

    public String srandmembers(String key, String num) throws IOException {

        String command = "srandmembers " + key + " " + num + " ";

        return sendCommand(command);
    }

    public String sismember(String key, String[] values) throws IOException {

        String command = "sismember " + key + " ";
        for (int i = 0; i < values.length; i++) {
            command = command + values[i] + " ";
        }

        return sendCommand(command);
    }

    public String sunion(String[] keys) throws IOException {

        String command = "sunion ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String smembers(String key) throws IOException {

        String command = "smembers " + key + " ";

        return sendCommand(command);
    }

    public String srem(String key, String[] values) throws IOException {

        String command = "srem " + key + " ";
        for (int i = 0; i < values.length; i++) {
            command = command + values[i] + " ";
        }

        return sendCommand(command);
    }

    public String setAdd(String key, String[] values) throws IOException {

        String command = "sadd " + key + " ";
        for (int i = 0; i < values.length; i++) {
            command = command + values[i] + " ";
        }

        return sendCommand(command);
    }

    public String lpop(String key) throws IOException {

        String command = "lpop " + key;
        return sendCommand(command);
    }

    public String rpop(String key) throws IOException {

        String command = "rpop " + key;
        return sendCommand(command);
    }

    public String llen(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "llen ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String lrange(String key, String start, String end) throws IOException {

        String command = "lrange ";

        command = command + key + " " + end + " ";

        return sendCommand(command);
    }

    public String rrange(String key, String start, String end) throws IOException {

        String command = "rrange ";

        command = command + key + " " + end + " ";

        return sendCommand(command);
    }

    public String lpush(String key, String[] values) throws IOException {

        if (keys.length != values.length || keys.length == 0) {
            return null;
        }

        String command = "lpush " + key + " ";
        for (int i = 0; i < values.length; i++) {
            command = command + values[i] + " ";
        }

        return sendCommand(command);
    }

    public String rpush(String[] keys, String[] values) throws IOException {

        if (keys.length != values.length || keys.length == 0) {
            return null;
        }

        String command = "rpush ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " " + values[i] + " ";
        }

        return sendCommand(command);
    }

    String persist(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "persist ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String ttl(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "ttl ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String expire(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "expire ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String exists(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "exists ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String incr(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "incr ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String decr(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "decr ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String strlen(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "strlen ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String del(String key) throws IOException {

        String command = "del " + key;
        return sendCommand(command);
    }

    public String get(String key) throws IOException {

        String command = "get " + key;
        return sendCommand(command);
    }

    public String get(String[] keys) throws IOException {

        if (keys.length < 1) {
            return null;
        }

        String command = "get ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " ";
        }

        return sendCommand(command);
    }

    public String set(String key, String value) throws IOException {

        String command = "set " + key + " " + value;
        return sendCommand(command);
    }

    public String set(String[] keys, String[] values) throws IOException {

        if (keys.length != values.length || keys.length == 0) {
            return null;
        }

        String command = "set ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " " + values[i] + " ";
        }

        return sendCommand(command);
    }

    public String set(String key, String value, String expireTime) throws IOException {

        String command = "set " + key + " " + value + " " + "expire" + " " + expireTime;
        return sendCommand(command);
    }

    public String set(String[] keys, String[] values, String expireTime) throws IOException {

        if (keys.length != values.length || keys.length == 0) {
            return null;
        }

        String command = "set ";
        for (int i = 0; i < keys.length; i++) {
            command = command + keys[i] + " " + values[i] + " ";
        }

        command = command + "expire " + expireTime;

        return sendCommand(command);
    }

    public static void main(String[] args) {

        try {
            Socket socket = new Socket("127.0.0.1", 4999);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // bufferedWriter.write("hset hash0 name0 value0 name1 value1\r\n");
            // bufferedWriter.write("hget hash0\r\n");
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
            // bufferedWriter.write("rpush list1 value123 value987\r\n");
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
            // bufferedWriter.write("exists key0 key1 key2 key3\r\n");
            // bufferedWriter.write("expire key0 9\r\n");
            // bufferedWriter.write("ttl key0 key1 key2 key5 key6\r\n");
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
