package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;

public class PushHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        System.out.println("PUSH COMMAND OPERATION......");
        RedisResponse response = new RedisResponse();
        String result = "";
        if (strings.length < 3) {
            response.setMessage("two argument needed at least");
            return response;
        }
        String comand = strings[0].toUpperCase();
        String key = strings[1];

        Storage storage = Storage.getStorage();
        for (int i = 2; i < strings.length; i++) {
            switch (comand) {
                case "RPUSH":
                    storage.listPush(key, strings[i], false);
                    break;
                case "LPUSH":
                    storage.listPush(key, strings[i], true);
                    break;

            }
        }

        result = "1";
        response.setMessage(result);
        return response;
    }

}
