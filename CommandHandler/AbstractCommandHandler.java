package CommandHandler;

import java.util.ArrayList;

import Response.RedisResponse;

public abstract class AbstractCommandHandler {
    // public ArrayList<String> stringBuffer;
    public abstract RedisResponse operation(String[] strings);
}
