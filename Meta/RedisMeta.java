package Meta;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class RedisMeta {
    public static ArrayList<String> COMMANDS = new ArrayList<>(
            Arrays.asList("GET", "SET", "EXPIRE", "DEL", "STRLEN", "EXISTS", "INCR", "DECR", "TTL"));
}
