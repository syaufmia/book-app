package bk.app.ui;


import bk.set.Author;
import bk.set.Book;

import java.io.*;
import java.util.*;

public class FileManager {



    private List <Object> writingList = new ArrayList<>();
    private final List <String[]> readingList = new ArrayList<>();


    static final String AUTHOR_TABLE_HEADER_ROW = "AuthorID,Vorname,Nachname";
    static final String BOOK_TABLE_HEADER_ROW = "AuthorID,Titel,ISBN,Verlag,Erscheinungsjahr";
    static final String AUTHOR_TABLE_FILE_NAME = "AuthorTest.csv";
    static final String BOOK_TABLE_FILE_NAME = "BookTest.csv";

    //TODO: CHECK WRITE AND READ KEY:VALUE PAIR ON JSON FILE

    public void writeBookMapToCSV (Map<Integer, List<Book>> map, String path, String header) {
        File filePath = new File(path);

        try {
            FileWriter fw = new FileWriter (filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(header);
            for (Integer key : map.keySet()) {
                for (Book book : map.get(key)) {
                    bw.newLine();
                    bw.write(key.toString());
                    bw.write(",");
                    bw.write(book.toStringNoID());
                }
            }
//            for (List<Book> value : map.values()) {
//                for (Book book : value) {
//                    bw.write(book.toString());
//                    bw.newLine();
//                }
//            }
            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public Map<Integer, List<Book>> readBookListFromCSV (String path) {
//        readingList.clear();
//        File filePath = new File(path);
//        try {
//            FileReader fr = new FileReader(filePath);
//            BufferedReader br = new BufferedReader(fr);
//            br.readLine(); //header line
//            String line = br.readLine();
//            while (!line.equalsIgnoreCase("")) {
//                readingList.add(line.split(","));
//                line = br.readLine();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NullPointerException ignored) {}
//        return readingList;
//    }

    private File getFile (String fileName) {
        return new File(Objects.requireNonNull(FileManager.class.getClassLoader().getResource(fileName)).getPath());
    }




    public void writeAuthorMapToCSV (Map<Integer, Author> map, String path, String header) {
        File filePath = null;

        try {
            filePath = getFile(path);
            FileWriter fw = new FileWriter (filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(header);
            for (Integer key : map.keySet()) {
                bw.newLine();
                bw.write(key.toString());
                bw.write(",");
                bw.write(map.get(key).toStringNoID());

            }
            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeObjectFileCSV (List<Object> writingList, String path, String header) {
        File filePath = null;
        this.writingList.clear();
        this.writingList = writingList;

        try {
            filePath = getFile(path);
            FileWriter fw = new FileWriter (filePath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(header);
            for (Object o : writingList) {
                bw.newLine();
                bw.write(o.toString());
            }
            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String[]> readCSVFileAsObjects (String path) {
        readingList.clear();
        File filePath = null;
        try {
            filePath = getFile(path);
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            br.readLine();
            String line = br.readLine();
            while (!line.equalsIgnoreCase("")) {
                readingList.add(line.split(","));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException ignored) {}
        return readingList;
    }
}
