package Policies;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

import Storage.Storage;

public class RDBPersistence extends Persistence {

    long time = 10L;
    File file ;


    public void setTime(long time) {
        this.time = time * 1000;
     
    }
    public void setFile(String filePath){
        file = new File(filePath);
    }

    @Override
    public void run() {
        Storage storage = Storage.getStorage();
        while (running) {
            long currenTime = new Date().getTime();
            if (currenTime % time == 0) {

                ObjectOutputStream oout;
                try {
                    oout = new ObjectOutputStream(new FileOutputStream(file));
                    oout.writeObject(storage);
                    oout.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        }

    }
}
