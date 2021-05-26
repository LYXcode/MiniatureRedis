package DataType;

import java.util.ArrayList;

public class ListType extends DataType{
    public ArrayList<String> dataList = new ArrayList<>();

    public ArrayList<String> getDataList() {
        return dataList;
    }

    public void setDataList(ArrayList<String> dataList) {
        this.dataList = dataList;
    }
}
