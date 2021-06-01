package CommandHandler;

public class CommandHandlerFactory {
    public static AbstractCommandHandler getCommandHandler(String command){
        if(command.toUpperCase().equals("GET")){
            return new SetHandler();
        }


        return null;
    }
}
