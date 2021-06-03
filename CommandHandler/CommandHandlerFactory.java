package CommandHandler;

public class CommandHandlerFactory {
    public static AbstractCommandHandler getCommandHandler(String command) {
        AbstractCommandHandler commandHandler;
        switch (command.toUpperCase()) {
            case "SET":
                commandHandler = new SetHandler();
                break;

            case "GET":
                commandHandler = new GetHandler();
                break;

            case "DEL":
                commandHandler = new DelHandler();
                break;

            case "STRLEN":
                commandHandler = new StrlenHandler();
                break;
            case "INCR":
            case "DECR":
                commandHandler = new IncrDecrHandler();
                break;

            case "EXISTS":
                commandHandler = new ExistsHandler();
                break;

            case "EXPIRE":
            case "TTL":
                commandHandler = new ExpireHandler();
                break;

            default:
                commandHandler = null;

        }

        return commandHandler;
    }
}
