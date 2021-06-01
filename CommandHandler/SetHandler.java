package CommandHandler;

import java.util.ArrayList;

import Response.RedisResponse;

public class SetHandler extends AbstractCommandHandler{
    // public ArrayList<String> stringBuffer;


    public RedisResponse operation(String[] strings) {

        RedisResponse response = new RedisResponse();
        for (String s : strings) {
            System.out.println(s);
        }
    }
}
