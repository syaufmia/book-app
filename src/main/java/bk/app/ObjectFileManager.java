package bk.app;

import bk.app.ui.FileManager;
import bk.control.setcntrl.BookDao;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ObjectFileManager {

    private File getFile (String fileName) {
        return new File(Objects.requireNonNull(FileManager.class.getClassLoader().getResource(fileName)).getPath());
    }

    private List <Object> writingList = new ArrayList<>();
    private List <Object> readingList = new ArrayList<>();


    public void writeObjectFile (List<Object> writingList, String path) {
        File file = null;
        this.writingList.clear();
        this.writingList = writingList;

        try {
            file = new File(path);
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            for (Object o : writingList) {
                objOut.writeObject(o);
            }
            objOut.close();
            fileOut.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Object> readObjectFile (String path) {
        readingList.clear();
        File file;
        Object o;
        try {
            file = new File(path);
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objIn = new ObjectInputStream(fileIn);
            try {
                while (true) {
                    o = objIn.readObject();
                    readingList.add(o);
                }
            } catch (EOFException ignored) { //TODO: check how to NOT work with exception here
            }
            objIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    return readingList;
    }
}
