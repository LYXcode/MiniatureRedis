package CommandHandler;

import java.util.ArrayList;

import DataType.StringType;
import Response.RedisResponse;
import Storage.Storage;
import Utils.RedisUtils;

public class SetHandler extends AbstractCommandHandler {
    // public ArrayList<String> stringBuffer;

    public RedisResponse operation(String[] strings) {
        ArrayList<String> keyValuePair = new ArrayList<>();
        String expireTime = "0";

        RedisResponse response = new RedisResponse();
        if (strings.length % 2 == 0) {
            response.setMessage(
                    "SET KEY VALUE [EXPIRE TIME] expected! but " + String.valueOf(strings.length - 1) + " received.");
        } else {
            for (int i = 1; i < strings.length; i++) {
                if (strings[i].toUpperCase().equals("EXPIRE")) {

                    if (i + 1 >= strings.length) {
                        response.setMessage("expire need one argument!");
                        return response;
                    }
                    expireTime = strings[i + 1];
                    if (!RedisUtils.isNumericString(expireTime)) {
                        response.setMessage("expire argument should be number, which is not less than 0!");
                        return response;
                    }

                    ++i;
                    continue;

                }

                keyValuePair.add(strings[i]);
                System.out.println(strings[i]);
            }
            System.out.println(keyValuePair.size());

            for (int i = 0; i < keyValuePair.size(); i++) {
                if (i % 2 == 0) {
                    StringType stringData = new StringType();
                    stringData.setData(keyValuePair.get(i + 1));
                    if (!expireTime.equals("0")) {
                        Long et = Long.valueOf(expireTime);
                        stringData.setExpireTime(et);
                    }
                    Storage.getStorage().stringStorage.put(keyValuePair.get(i), stringData);
                }
            }

            System.out.println("expire time " + expireTime);
            response.setMessage("OK");

            System.out.println(Storage.getStorage().stringStorage.get("key").getData());
            System.out.println(Storage.getStorage().stringStorage.get("key").getExpireTime());
        }

        return response;
    }
}
