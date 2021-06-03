package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;

public class DelHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        System.out.println("DEL COMMAND OPERATION......");
        int count = 0;

        for (int i = 1; i < strings.length; i++) {
            System.out.println(strings[i]);
            String key = strings[i];
            Storage.getStorage().deleteKey(key);
            count++;

        }

        String message = count == (strings.length - 1) ? "1" : "0";
        response.setMessage(message);

        return response;
    }
}
