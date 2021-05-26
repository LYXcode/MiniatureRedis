package DataType;

import java.util.HashMap;

public class HashType extends DataType{
    public HashMap<String, String> dataHash = new HashMap<>();

    public HashMap<String, String> getDataHash() {
        return dataHash;
    }

    public void setDataHash(HashMap<String, String> dataHash) {
        this.dataHash = dataHash;
    }
}
