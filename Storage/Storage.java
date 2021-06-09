package Storage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javax.xml.crypto.Data;

import DataType.DataType;
import DataType.HashType;
import DataType.ListType;
import DataType.SetType;
import DataType.StringType;
import Meta.Types;
import Utils.RedisUtils;


public class Storage implements Serializable {
    private static final long serialVersionUID = 3472506294868168617L;
    private volatile static Storage storage;

    private volatile HashMap<String, DataType> stringStorage;
    private volatile HashMap<String, DataType> listStorage;
    private volatile HashMap<String, DataType> hashStorage;
    private volatile HashMap<String, DataType> setStorage;
    public volatile HashMap<String, String> keyPool;
    private volatile HashMap<String, HashMap<String, DataType>> StorageMap;
    private volatile String[] dataTypes = { "STRING", "LIST", "SET", "HASH" };
    public String testInfo = "0";

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

    public static void recoverData(String recoverPath){
        ObjectInputStream oin;
        try {
            oin = new ObjectInputStream(new FileInputStream(recoverPath));
            storage = (Storage) oin.readObject();
            oin.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean setString(String key, StringType value) {

        return addKeyValue(key, dataTypes[0], value);

    }

    public StringType getString(String key) {
        if (keyPool.containsKey(key) && StorageMap.get(keyPool.get(key)).containsKey(key)) {
            try {
                return (StringType) StorageMap.get(keyPool.get(key)).get(key);
            } catch (Exception e) {
                return null;
            }
        } else {
            return null;
        }
    }

    public int persist(String key) {
        DataType data = getDataFromKey(key);
        if (key == null) {
            return 0;
        }

        data.setExpireTime(0L);
        return 1;
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

    public synchronized long ttl(String key) {
        if (!keyPool.containsKey(key)) {
            return -1;
        }

        String type = keyPool.get(key);
        DataType data = StorageMap.get(type).get(key);

        if (data.getExpireTime() == 0L) {
            return -1;
        }

        long currentTime = new Date().getTime();

        long ttl = data.getExpireTime() - currentTime;

        if (ttl < 0L) {
            deleteKey(key);
            return -1;
        }

        return ttl / 1000;
    }

    public synchronized int setAdd(String key, String value) {
        String type = dataTypes[2];
        DataType set;
        if (exists(key) && checkKeyValidation(key, type)) {
            set = getDataFromKey(key);
            try {
                set.getSetData().add(value);
            } catch (Exception e) {
                return 0;
            }
        } else {
            if (!checkKeyValidation(key, type)) {
                return 0;
            }
            set = new SetType();
            set.getSetData().add(value);
            StorageMap.get(type).put(key, set);
            keyPool.put(key, type);
        }

        return 1;

    }

    public synchronized int setRemove(String key, String value) {
        String type = dataTypes[2];
        ttl(key);
        if ((!exists(key)) || (!checkKeyValidation(key, type))) {
            return 0;
        }
        int result = 0;
        DataType data = getDataFromKey(key);
        try {
            result = data.getSetData().remove(value) ? 1 : 0;
        } catch (Exception e) {
            return 0;
        }
        return result;
    }

    public int setIsMenber(String key, String value) {
        ttl(key);
        DataType data = getDataFromKey(key);

        int result = 0;
        try {
            result = data.getSetData().contains(value) ? 1 : 0;
        } catch (Exception e) {
            return 0;
        }

        return result;
    }

    public String setMembers(String key) {
        ttl(key);
        if (!exists(key)) {
            return null;
        }
        String result = "";
        try {
            DataType data = getDataFromKey(key);

            Object[] results = data.getSetData().toArray();

            for (int i = 0; i < results.length; i++) {
                result = result + results[i].toString() + " ";
            }

        } catch (Exception e) {

            return null;
        }

        return result;
    }

    public String setUnion(String[] keys) {
        String result = "";

        HashSet<String> rSet = new HashSet<>();
        for (int i = 0; i < keys.length; i++) {
            String key = keys[i];
            ttl(key);
            if (!exists(key)) {

                return null;
            }

            try {
                DataType data = getDataFromKey(key);
                HashSet<String> keySet = data.getSetData();
                Object[] contents = keySet.toArray();
                for (int j = 0; j < contents.length; j++) {
                    if (rSet.contains(contents[j].toString())) {
                        continue;
                    }

                    rSet.add(contents[j].toString());
                }
            } catch (Exception e) {
                return null;
            }
        }

        Object[] res = rSet.toArray();
        for (int i = 0; i < res.length; i++) {
            result = result + res[i].toString() + " ";
        }
        return result;
    }

    public synchronized String setPop(String key, int numbers) {
        ttl(key);
        if (!exists(key) || numbers < 1) {
            return null;
        }
        DataType data = getDataFromKey(key);
        String result = "";
        try {
            Object[] res = data.getSetData().toArray();
            if (numbers > res.length) {
                numbers = res.length;
            }

            for (int i = 0; i < numbers; i++) {
                result = result + res[i].toString() + " ";
                data.getSetData().remove(res[i].toString());
            }
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    public synchronized String setRandMembers(String key, int numbers) {
        ttl(key);
        if (!exists(key) || numbers < 1) {
            return null;
        }
        DataType data = getDataFromKey(key);
        String result = "";
        try {
            Object[] res = data.getSetData().toArray();
            if (numbers > res.length) {
                numbers = res.length;
            }

            for (int i = 0; i < numbers; i++) {
                result = result + res[i].toString() + " ";

            }
        } catch (Exception e) {
            return null;
        }

        return result;
    }

    public synchronized int hashSet(String key, String name, String value) {
        String type = dataTypes[3];
        if (exists(key)) {
            try {
                DataType data = getDataFromKey(key);
                data.getDataHash().put(name, value);
            } catch (Exception e) {
                return 0;
            }
        } else {
            if (!checkKeyValidation(key, type)) {
                return 0;
            }
            HashType hash = new HashType();
            hash.getDataHash().put(name, value);
            StorageMap.get(type).put(key, hash);
            keyPool.put(key, type);
        }

        return 1;
    }

    public String hashGet(String key, String[] names) {
        ttl(key);
        if (!exists(key)) {
            return null;
        }

        DataType data = getDataFromKey(key);
        String result = "";
        try {
            HashMap<String, String> hash = data.getDataHash();
            if (names == null || names.length == 0) {
                for (String name : hash.keySet()) {
                    result = name + " " + hash.get(name) + " " + result;
                }
            } else {
                for (String name : names) {
                    result = result + hash.get(name) + " ";
                }
            }
        } catch (Exception e) {
            return null;
        }

        return result;

    }

    public synchronized int hashDelete(String key, String name) {
        ttl(key);
        if (!exists(key)) {
            return 0;
        }
        int result = 0;

        DataType data = getDataFromKey(key);
        try {
            HashMap<String, String> hash = data.getDataHash();
            if (hash.containsKey(name)) {
                hash.remove(name);
                result = 1;
            } else {
                result = 0;
            }

        } catch (Exception e) {
            return 0;
        }

        return result;
    }

    public synchronized int listPush(String key, String value, boolean leftPush) {
        String type = dataTypes[1];
        if (exists(key)) {
            try {
                DataType data = getDataFromKey(key);
                if (!leftPush) {
                    data.getDataList().add(value);
                } else {
                    data.getDataList().add(0, value);
                }
            } catch (Exception e) {
                return 0;
            }

        }

        else {
            if (!checkKeyValidation(key, type)) {
                return 0;
            }
            ListType list = new ListType();
            list.getDataList().add(value);
            StorageMap.get(type).put(key, list);
            keyPool.put(key, type);
        }
        return 1;
    }

    public String listPop(String key, boolean rightPop) {
        if (!exists(key)) {
            return null;
        }

        ttl(key);
        DataType list = getDataFromKey(key);
        if (list == null) {
            return null;
        }
        String result = "";

        try {
            ArrayList<String> listData = list.getDataList();
            if (rightPop) {
                result = listData.remove(listData.size() - 1);
            } else {
                result = listData.remove(0);
            }

        } catch (Exception e) {
            return null;
        }

        return result;
    }

    public String listLength(String key) {
        if (!exists(key)) {
            return "-1";
        }

        ttl(key);
        DataType list = getDataFromKey(key);
        if (list == null) {
            return "-1";
        }
        String result = "";

        try {
            ArrayList<String> listData = list.getDataList();
            result = String.valueOf(listData.size());
        } catch (Exception e) {
            return "-1";
        }

        return result;
    }

    public String listRange(String key, int start, int end, boolean rightRange) {
        if (!exists(key)) {
            return null;
        }

        ttl(key);
        DataType list = getDataFromKey(key);
        if (list == null) {
            return null;
        }
        String result = "";

        try {
            ArrayList<String> listData = list.getDataList();
            int size = listData.size();
            int startPos = 0, endPos = 0;
            if (start == -1 && end == -1) {
                startPos = 0;
                endPos = size - 1;
            } else {
                startPos = start <= 0 ? 0 : (start > size - 1 ? size - 1 : start);
                endPos = end > size - 1 ? size - 1 : (end <= 0 ? 0 : end);
            }

            if (startPos > endPos || startPos > size - 1) {
                return null;
            }
            if (rightRange) {
                for (int i = size - startPos - 1; i >= size - endPos - 1; i--) {
                    result = result + listData.get(i) + " ";
                }
            } else {
                for (int i = startPos; i <= endPos; i++) {
                    result = result + listData.get(i) + " ";
                }

            }
        } catch (Exception e) {
            return null;
        }

        return result;
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
            if (!(this.keyPool.get(key).equals(type))) {
                return false;
            }
        }

        return true;
    }

}
