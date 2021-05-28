package CommandHandler;

import java.util.ArrayList;

public class SetHandler {
    public ArrayList<String> stringBuffer;

    public SetHandler(ArrayList<String> stringBuffer) {
        this.stringBuffer = stringBuffer;
        String[] strings = RedisUtils.parseCommandString(stringBuffer.get(0));
        operation(strings);
    }

    private void operation(String[] strings) {
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
