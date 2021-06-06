package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;
import Utils.RedisUtils;

public class RangeHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        System.out.println("RANGE COMMAND OPERATION......");
        RedisResponse response = new RedisResponse();
        String result = "";
        int start = -1;
        int end = -1;
        if (strings.length < 2 || strings.length > 4) {
            response.setMessage("two arguments needed at least, but four at most");
            return response;
        }

        if (strings.length == 3) {

            response.setMessage("two position arguments needed , not less than 0");

        }

        if (strings.length == 4) {

            if (RedisUtils.isNumericString(strings[2]) && (Integer.valueOf(strings[2]) >= 0)
                    && RedisUtils.isNumericString(strings[3]) && (Integer.valueOf(strings[3]) >= 0)) {
                start = Integer.valueOf(strings[2]);
                end = Integer.valueOf(strings[3]);

            } else {
                response.setMessage("position argument should be number, not less than 0");
                return response;
            }
        }

        String command = strings[0].toUpperCase();
        String key = strings[1];
        Storage storage = Storage.getStorage();
        switch (command) {
            case "RRANGE":
                result = storage.listRange(key, start, end, true);
                break;
            case "LRANGE":
                result = storage.listRange(key, start, end, false);

        }
        if(result == null){
            result = "no such key in list";
        }

        response.setMessage(result);
        return response;
    }
}
