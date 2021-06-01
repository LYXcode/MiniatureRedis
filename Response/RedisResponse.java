package Response;
public class RedisResponse {
    public String statue = "";
    public String message = "";
    public String getStatue() {
        return statue;
    }
    public void setStatue(String statue) {
        this.statue = statue;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String toString(){
        return statue + message + "\r\n";
    }
}
