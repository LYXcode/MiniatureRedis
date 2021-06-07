package DataType;

import java.util.HashSet;

public class SetType extends DataType{
    public HashSet<String> setData = new HashSet<>();

    public HashSet<String> getSetData() {
        return setData;
    }

    public void setSetData(HashSet<String> setData) {
        this.setData = setData;
    }
}
