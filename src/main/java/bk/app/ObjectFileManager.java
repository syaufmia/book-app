package bk.app;

import bk.control.setcntrl.BookDao;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ObjectFileManager {


    private static final String mainListSource = "C:\\dev\\projects\\buchverwaltung\\src\\main\\java\\bk\\data\\booklist\\completebooklist.ser";
    private static final String mainListTable = "C:\\dev\\projects\\buchverwaltung\\src\\main\\java\\bk\\data\\csvfiles\\completebooklist.csv";

    File file = new File(mainListSource);
    File file2 = new File(mainListTable);


    private List <Object> writingList = new ArrayList<>();
    private List <Object> readingList = new ArrayList<>();


    public void writeObjectFile (List<Object> writingList, String path) {
        this.writingList.clear();
        this.writingList = writingList;

        try {
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
        Object o;
        try {
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
