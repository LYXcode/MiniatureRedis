package Storage;

import java.util.Date;
import java.util.HashMap;

import javax.xml.crypto.Data;

import DataType.DataType;
import DataType.HashType;
import DataType.ListType;
import DataType.SetType;
import DataType.StringType;
import Meta.Types;
import Utils.RedisUtils;

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

        return addKeyValue(key, dataTypes[0], value);

    }

    public StringType getString(String key) {
        if (keyPool.containsKey(key) && StorageMap.get(keyPool.get(key)).containsKey(key)) {
            return (StringType) StorageMap.get(keyPool.get(key)).get(key);
        } else {
            return null;
        }
    }

    public int expire(String key, String expireTime) {
        int result = 0;
        if (!Storage.getStorage().exists(key)) {
            result = 0;
        } else {
            if (!RedisUtils.isNumericString(expireTime) || Long.valueOf(expireTime) < 0) {
                result = 0;
            }

            else {

                DataType data = getDataFromKey(key);
                long currenTime = new Date().getTime();

                data.setExpireTime((currenTime + 1000 * Long.valueOf(expireTime)));

                result = 1;

            }
        }

        return result;
    }

    public long ttl(String key) {
        if (!keyPool.containsKey(key)) {
            return -1;
        }
        String type = keyPool.get(key);
        DataType data = StorageMap.get(type).get(key);
        long currentTime = new Date().getTime();

        long ttl = data.getExpireTime() - currentTime;

        if (ttl < 0L) {
            deleteKey(key);
            return -1;
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

    public boolean deleteKey(String key) {
        if (!keyPool.containsKey(key)) {
            return true;
        }

        String type = keyPool.get(key);
        StorageMap.get(type).remove(key);
        keyPool.remove(key);
        return true;
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
