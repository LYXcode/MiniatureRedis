package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;

public class ExistsHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        System.out.println("EXISTS COMMAND OPERATION......");
        String result = "";
        for (int i = 1; i < strings.length; i++) {
            String key = strings[i];
            if (Storage.getStorage().exists(key)) {
                result = result + "1" + " ";
            } else {
                result = result + "0" + " ";
            }
        }
        response.setMessage(result);
        return response;
    }
}
