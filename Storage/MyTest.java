package Storage;

import java.util.HashMap;

import DataType.DataType;
import DataType.SetType;
import DataType.StringType;


public class MyTest {
public static void main(String[] args) {
    HashMap<String, DataType> d = new HashMap<>();
    StringType s = new StringType();
    s.setData("data");
    d.put("key", s);
    d.put("d", new SetType());

    System.out.println(d.get("key").getData());
    System.out.println(d.get("d").getData());
    

}
}
