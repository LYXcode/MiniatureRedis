package Storage;
import java.util.HashMap;

import DataType.HashType;
import DataType.ListType;
import DataType.SetType;
import DataType.StringType;

public class Storage {
    private volatile static Storage storage;

    public volatile HashMap<String, StringType> stringStorage;
    public volatile HashMap<String, ListType> listStorage;
    public volatile HashMap<String, HashType> hashStorage;
    public volatile HashMap<String, SetType> setStorage;

    private Storage(){
        stringStorage = new HashMap<>();
        listStorage = new HashMap<>();
        hashStorage = new HashMap<>();
        setStorage = new HashMap<>();
    }

    public static Storage getStorage(){
        if(storage == null){
            synchronized(Storage.class){
                if(storage == null){
                    storage = new Storage();
                }
            }
        }
    return storage;
    }

    public void read(){}

    public synchronized void write(){}
}
