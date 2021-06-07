package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;

public class SetAddRemHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        System.out.println("SET ADD AND REMOVE COMMAND OPERATION......");
        String result = "";
        if (strings.length < 3) {
            response.setMessage("wrong arguments,3 at least");
            return response;
        }
        String command = strings[0].toUpperCase();
        String key = strings[1];
        Storage storage = Storage.getStorage();

        for (int i = 2; i < strings.length; i++) {
            switch (command) {
                case "SADD":
                    int res = storage.setAdd(key, strings[i]);
                    result = result + String.valueOf(res) + " ";
                    break;
                case "SREM":
                    res = storage.setRemove(key, strings[i]);
                    result = result + String.valueOf(res) + " ";
                    break;

            }
        }

        response.setMessage(result);
        return response;

    }
}
