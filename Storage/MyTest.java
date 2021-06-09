package Storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import DataType.DataType;
import DataType.SetType;
import DataType.StringType;

public class MyTest {
    public static void main(String[] args) {

        Storage.recoverData("./DataBackup/persistence.out");
        System.out.println(Storage.getStorage().testInfo);
        StringType value =  new StringType();
        value.setData("data");
        System.out.println(Storage.getStorage().setString("key",value));
        System.out.println(Storage.getStorage().getString("key").getData());


    }
}
