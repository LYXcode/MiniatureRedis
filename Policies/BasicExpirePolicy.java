package Policies;

import java.util.Date;

import Storage.Storage;

public class BasicExpirePolicy extends ExpirePolicy {

    long time = 10L;

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public void run() {
        Storage storage = Storage.getStorage();

        while (running) {
            long currenTime = new Date().getTime();
            if (currenTime % time == 0) {
                for (String key : storage.keyPool.keySet()) {
                    storage.ttl(key);
                }
            }
        }

    }

}
