package Utils;

import java.util.regex.Pattern;

public class RedisUtils {
    public static String[] parseCommandString(String commandString) {
        
        // int currentPos = 0;
        // for (int i = 0; i < commandString.length(); i++) {
        //     char c = commandString.charAt(i);
        //     if (c == ' ') {
        //         currentPos++;
        //     } else {
        //         break;
        //     }
        // }

        // commandString = commandString.substring(currentPos, commandString.length());

        // return commandString.split("\\s+");
    }

    public static boolean isNumericString(String string){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(string).matches();
    }
}
