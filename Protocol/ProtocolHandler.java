package Protocol;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.xml.crypto.Data;

import CommandHandler.AbstractCommandHandler;
import CommandHandler.CommandHandlerFactory;
import CommandHandler.SetHandler;
import DataType.DataType;
import Response.RedisResponse;

public class ProtocolHandler extends AbstractProtocolHandler {

    public Socket socket;
    public ProtocolHandler(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try {
            handleRequest();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void handleRequest() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        ArrayList<String> stringBuffer = new ArrayList<>();

        String content = bufferedReader.readLine();

        stringBuffer.add(content);

        String commandString = stringBuffer.get(0);
        System.out.println(commandString);
        String[] commandElement = commandString.split("\\s+");

        AbstractCommandHandler commandHandler = CommandHandlerFactory.getCommandHandler(commandElement[0]);
        RedisResponse response = new RedisResponse();
        if (commandHandler == null) {

            response.setMessage("no such command " + commandElement[0]);
        }

        response = commandHandler.operation(commandElement);

        bufferedWriter.write(response.toString());
        bufferedWriter.flush();
        bufferedWriter.close();

    }

}
