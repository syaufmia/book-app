package sum.ike.control;

import com.google.gson.Gson;

import java.io.*;
import java.util.*;

public class FileManager {

    private final List <String[]> readingList = new ArrayList<>();


    public static final String AUTHOR_TABLE_HEADER_ROW = "AuthorID,Vorname,Nachname";
    public static final String BOOK_TABLE_HEADER_ROW = "AuthorID,Titel,ISBN,Verlag,Erscheinungsjahr";
    public static final String USER_HEADER_ROW = "username,password,full name,UID";

    //TODO: CHECK WRITE AND READ KEY:VALUE PAIR ON JSON FILE

    public void writeObjectFileJson (List<Object> writingList, String fileName) {
        File file = getFile(fileName);
        Gson gson = new Gson();
        FileOutputStream fos;
        try {

            fos = new FileOutputStream(file);
            OutputStreamWriter bos = new OutputStreamWriter(fos);
            bos.write(gson.toJson(writingList));
            bos.close();
            fos.close();
        }
        catch (IOException | NullPointerException ignored) {
        }
    }


    private File getFile (String fileName) {
        return new File(Objects.requireNonNull(FileManager.class.getClassLoader().getResource(fileName)).getPath());
    }

    public void writeObjectFileCSV (List<Object> writingList, String fileName, String header) {
        File file = getFile(fileName);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file);
            OutputStreamWriter bos = new OutputStreamWriter(fos);
            bos.write(header);
            for (Object o : writingList) {
                bos.append("\n");
                bos.write(o.toString());
            }
            bos.close();
            fos.close();
        }
        catch (IOException | NullPointerException ignored) {
        }
    }

//    public void writeObjectFileCSV (List<Object> writingList, String path, String header) {
//        File filePath = new File(path);
//        this.writingList.clear();
//        this.writingList = writingList;
//
//        try {
//            FileWriter fw = new FileWriter (filePath);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(header);
//            for (Object o : writingList) {
//                bw.newLine();
//                bw.write(o.toString());
//            }
//            bw.close();
//            fw.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public List<String[]> readCSVFileAsObjects (String fileName) {
        readingList.clear();
        File file = getFile(fileName);
        InputStream is;
        try {
            is = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            StringBuilder sb = new StringBuilder();
            int data;
            while ((data = bis.read()) != -1) {
                sb.append((char) data);
            }
            bis.close();
            is.close();
            String[] byLine = sb.toString().split("[\\t\\n\\x0B\\f\\r]++");
            for (String s : byLine) {
                readingList.add(s.split(","));
            }
            readingList.remove(0);
        } catch (IOException | NullPointerException ignored) {
        }
        return readingList;
    }
}
