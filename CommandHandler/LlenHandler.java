package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;

public class LlenHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        String result = "";
        for (int i = 1; i < strings.length; i++) {
            System.out.println(strings[i]);
            String key = strings[i];
            if (Storage.getStorage().exists(key)) {
                String len = Storage.getStorage().listLength(key);
                result = result + len + " ";

                continue;
            }
            if (!Storage.getStorage().exists(key)) {
                result = result + String.valueOf(-1) + " ";
                continue;
            }

        }

        response.setMessage(result);
        return response;
    }
}
