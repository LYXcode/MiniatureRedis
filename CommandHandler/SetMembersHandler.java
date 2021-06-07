package CommandHandler;

import Response.RedisResponse;
import Storage.Storage;
import Utils.RedisUtils;

public class SetMembersHandler extends AbstractCommandHandler {
    public RedisResponse operation(String[] strings) {
        RedisResponse response = new RedisResponse();
        System.out.println("SET MEMBERS COMMAND OPERATION......");
        String result = "";
        if (strings.length < 2) {
            response.setMessage("wrong arguments,3 at least");
            return response;
        }
        String command = strings[0].toUpperCase();
        String key = strings[1];
        Storage storage = Storage.getStorage();
        switch (command) {
            case "SISMEMBER":
                if (strings.length < 3) {
                    response.setMessage("wrong arguments");
                    return response;
                }
                for (int i = 2; i < strings.length; i++) {
                    int res = storage.setIsMenber(key, strings[i]);
                    result = result + String.valueOf(res) + " ";

                }
                break;

            case "SMEMBERS":
                result = storage.setMembers(key);
                break;

            case "SUNION":
                String[] keys = new String[strings.length - 1];
                for (int i = 1; i < strings.length; i++) {
                    keys[i - 1] = strings[i];

                }
                result = storage.setUnion(keys);
                break;
            case "SRANDMEMBERS":
                if (strings.length != 3 || !RedisUtils.isNumericString(strings[2])) {

                    response.setMessage("wrong arguments");
                    return response;
                }
                result = storage.setRandMembers(key, Integer.valueOf(strings[2]));
                break;
            case "SPOP":
                if (strings.length != 3 || !RedisUtils.isNumericString(strings[2])) {
                    response.setMessage("wrong arguments");
                    return response;
                }
                result = storage.setPop(key, Integer.valueOf(strings[2]));
                break;

        }

        response.setMessage(result);
        return response;

    }
}
