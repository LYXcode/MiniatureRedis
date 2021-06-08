package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;

public class HashCommandsHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        System.out.println("HASH COMMANDS OPERATION......");
        if (strings.length < 2) {
            response.setMessage("wrong arguments");
            return response;
        }
        String command = strings[0].toUpperCase();
        String key = strings[1];
        Storage storage = Storage.getStorage();

        String message = "";

        switch (command) {
            case "HSET":
                if (strings.length <= 2 || strings.length % 2 != 0) {
                    response.setMessage("wrong arguments");

                    return response;
                }
                for (int i = 2; i < strings.length; i++) {

                    if (i % 2 != 0) {
                        continue;
                    }
                    String name = strings[i];
                    String value = strings[i + 1];
                    int res = storage.hashSet(key, name, value);
                    message += String.valueOf(res) + " ";

                }
                break;
            case "HGET":
                String[] names = null;
                if (strings.length == 2) {
                    names = null;
                } else {
                    names = new String[strings.length - 2];
                }

                for (int i = 2; i < strings.length; i++) {
                    names[i - 2] = strings[i];
                }
                message = storage.hashGet(key, names);
                break;
            case "HDEL":
                if (strings.length == 2) {
                    response.setMessage("wrong arguments");
                    return response;
                }
                for (int i = 2; i < strings.length; i++) {
                    String name = strings[i];
                    int res = storage.hashDelete(key, name);
                    message += String.valueOf(res) + " ";
                }
                break;
        }

        response.setMessage(message);

        return response;
    }
}