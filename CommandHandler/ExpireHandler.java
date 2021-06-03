package CommandHandler;

import java.util.Date;

import DataType.DataType;
import DataType.StringType;
import Response.RedisResponse;
import Storage.Storage;
import Utils.RedisUtils;

public class ExpireHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        System.out.println("EXPIRE COMMAND OPERATION......");

        String command = strings[0].toUpperCase();

        switch (command) {
            case "EXPIRE":
                response = expireOpearation(strings);
                break;

            case "TTL":
                response = ttlOperation(strings);
                break;

        }

        return response;
    }

    public RedisResponse ttlOperation(String[] strings) {
        RedisResponse response = new RedisResponse();
        String result = "";
        int stringNum = strings.length;
        if (stringNum < 2) {
            response.setMessage("format:ttl key0 key1......");
            return response;
        }
        Storage storage = Storage.getStorage();
        for (int i = 1; i < stringNum; i++) {
            String key = strings[i];
            long ttl = storage.ttl(key);
            result = result + String.valueOf(ttl) + " ";
        }

        response.setMessage(result);
        return response;
    }

    public RedisResponse expireOpearation(String[] strings) {
        RedisResponse response = new RedisResponse();
        String result = "";
        if (strings.length < 3 || strings.length % 2 != 1) {
            response.setMessage("format:set key0 time0 key1 time1 ...... ");
            return response;
        }
        for (int i = 1; i < strings.length - 1; i++) {
            if (i % 2 == 1) {
                String key = strings[i];
                String expireTime = strings[i + 1];
                int res = Storage.getStorage().expire(key, expireTime);
                result = result + String.valueOf(res) + " ";

            }
        }

        response.setMessage(result);
        return response;
    }

}
