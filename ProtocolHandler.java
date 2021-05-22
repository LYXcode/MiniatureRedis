import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class ProtocolHandler extends AbstractProtocolHandler {

    public void handleRequest(Socket socket) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ArrayList<String> stringBuffer = new ArrayList<>();
        while (true) {
            String content = bufferedReader.readLine();

            if (content == null) {
                break;
            }
            stringBuffer.add(content);
        }

        String firstString = stringBuffer.get(0);

        char dataType = firstString.charAt(0);

        switch (dataType) {
            case '+':
                System.out.println("string type");
                System.out.println(handleSimpleString(stringBuffer));
                break;

            case '-':
                System.out.println("error");
                break;

            case ':':
                System.out.println("integer");
                break;

            case '$':
                System.out.println("binary");
                break;

            case '*':
                System.out.println("array");
                break;

            case '%':
                System.out.println("dictionary");
                break;

            case '!':
                System.out.println("null");
                break;

            default:
                System.out.println("no such command");
        }

    }

    public String handleSimpleString(ArrayList<String> requestContents) {
        String contents = "";
        for (String content : requestContents) {
            contents += content;
        }

        return contents.substring(1, contents.length());
    }

    public void writeResponse() {

    }

}
