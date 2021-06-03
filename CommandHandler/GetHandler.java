package CommandHandler;

import DataType.StringType;
import Response.RedisResponse;
import Storage.Storage;

public class GetHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        System.out.println("GET COMMAND OPERATION......");
        String result = "";

        for (int i = 1; i < strings.length; i++) {
            StringType value = Storage.getStorage().getString(strings[i]);
            if (value == null) {
                result = result + null + " ";
            } else {
                String valueData = value.getData();
                result = result + valueData + " ";
            }

        }

        response.setMessage(result);

        return response;
    }
}
