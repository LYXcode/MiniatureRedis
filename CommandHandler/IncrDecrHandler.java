package CommandHandler;

import DataType.StringType;
import Response.RedisResponse;
import Storage.Storage;
import Utils.RedisUtils;

public class IncrDecrHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        String result = "";
        for (int i = 1; i < strings.length; i++) {
            System.out.println(strings[i]);
            String key = strings[i];
            if (Storage.getStorage().exists(key)) {
                String value = Storage.getStorage().getString(key).getData();
                if (RedisUtils.isNumericString(value)) {
                    int v = Integer.valueOf(value);
                    if (strings[0].toUpperCase().equals("INCR")) {
                        v++;
                    }
                    if (strings[0].toUpperCase().equals("DECR")) {
                        v--;
                    }

                    StringType valueData = new StringType();
                    valueData.setData(String.valueOf(v));
                    Storage.getStorage().setString(key, valueData);

                    result = result + String.valueOf(v) + " ";
                    continue;
                }

                else {
                    result = result + "NaN" + " ";
                    continue;
                }

            }
            if (!Storage.getStorage().exists(key)) {
                int v = 0;
                if (strings[0].toUpperCase().equals("INCR")) {
                    v++;
                }
                if (strings[0].toUpperCase().equals("DECR")) {
                    v--;
                }
                StringType newData = new StringType();
                newData.setData(String.valueOf(v));
                Storage.getStorage().setString(key, newData);
                result = result + String.valueOf(v) + " ";
                continue;
            }

        }

        response.setMessage(result);
        return response;
    }
}
