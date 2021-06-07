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
            case "PERSIST":
                commandHandler = new ExpireHandler();
                break;

            case "RPUSH":
            case "LPUSH":
                commandHandler = new PushHandler();
                break;

            case "RRANGE":
            case "LRANGE":
                commandHandler = new RangeHandler();
                break;

            case "LLEN":
                commandHandler = new LlenHandler();
                break;
            case "RPOP":
            case "LPOP":
                commandHandler = new PopHandler();
                break;
            case "SADD":
            case "SREM":
                commandHandler = new SetAddRemHandler();
                break;

            case "SISMEMBER":
            case "SMEMBERS":
            case "SUNION":
            case "SRANDMEMBERS":
            case "SPOP":
                commandHandler = new SetMembersHandler();
                break;
            default:
                commandHandler = null;

        }

        return commandHandler;
    }
}
