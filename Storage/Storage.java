package Storage;

import java.util.Date;
import java.util.HashMap;

import DataType.DataType;
import DataType.HashType;
import DataType.ListType;
import DataType.SetType;
import DataType.StringType;
import Meta.Types;

public class Storage {
    private volatile static Storage storage;

    private volatile HashMap<String, DataType> stringStorage;
    private volatile HashMap<String, DataType> listStorage;
    private volatile HashMap<String, DataType> hashStorage;
    private volatile HashMap<String, DataType> setStorage;
    private volatile HashMap<String, String> keyPool;
    private volatile HashMap<String, HashMap<String, DataType>> StorageMap;
    private volatile String[] dataTypes = { "STRING", "LIST", "SET", "HASH" };

    private Storage() {
        stringStorage = new HashMap<>();
        listStorage = new HashMap<>();
        hashStorage = new HashMap<>();
        setStorage = new HashMap<>();
        StorageMap = new HashMap<>();
        keyPool = new HashMap<>();

        StorageMap.put(dataTypes[0], stringStorage);
        StorageMap.put(dataTypes[1], listStorage);
        StorageMap.put(dataTypes[2], setStorage);
        StorageMap.put(dataTypes[3], hashStorage);
    }

    public static Storage getStorage() {
        if (storage == null) {
            synchronized (Storage.class) {
                if (storage == null) {
                    storage = new Storage();
                }
            }
        }
        return storage;
    }

    public boolean setString(String key, StringType value) {
        // if (!checkKeyValidation(key, dataTypes[0])) {
        //     return false;
        // }

        // StorageMap.get(dataTypes[0]).put(key, value);
        // keyPool.put(key, dataTypes[0]);

        return addKeyValue(key, dataTypes[0], value);
        
    }

    public StringType getString(String key) {
        if (keyPool.containsKey(key) && StorageMap.get(keyPool.get(key)).containsKey(key)) {
            return (StringType) StorageMap.get(keyPool.get(key)).get(key);
        } else {
            return null;
        }
    }

    public long ttl(String key) {
        if (!keyPool.containsKey(key)) {
            return -1;
        }
        String type = keyPool.get(key);
        DataType data = StorageMap.get(type).get(key);
        long currentTime = new Date().getTime();
        long ttl = data.getExpireTime() - currentTime;

        if (ttl < 0) {
            removeKeyValue(key);
            ttl = -1;
        }

        return ttl / 1000;
    }

    public boolean addKeyValue(String key, String type, DataType value) {
        if (!checkKeyValidation(key, type)) {
            return false;
        }

        StorageMap.get(type).put(key, value);
        keyPool.put(key, type);
        return true;
    }

    public void removeKeyValue(String key) {
        if (keyPool.containsKey(key)) {
            String type = keyPool.get(key);
            StorageMap.get(type).remove(key);
            keyPool.remove(key);

        }
    }

    public DataType getDataFromKey(String key) {
        if (!keyPool.containsKey(key)) {
            return null;
        }

        String type = keyPool.get(key);
        return StorageMap.get(type).get(key);
    }

    public boolean exists(String key) {
        return keyPool.containsKey(key);
    }

    public boolean deleteKey(String key) {
        if (!keyPool.containsKey(key)) {
            return true;
        }

        String type = keyPool.get(key);
        StorageMap.get(type).remove(key);
        keyPool.remove(key);
        return true;
    }

    public boolean checkKeyValidation(String key, String type) {
        if (this.keyPool.containsKey(key)) {
            if (!(this.keyPool.get(key) == type)) {
                return false;
            }
        }

        return true;
    }

    public synchronized void write() {
    }
}
