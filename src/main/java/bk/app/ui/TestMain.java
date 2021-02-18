package bk.app.ui;

import bk.app.ScanManager;
import bk.control.setcntrl.AuthorDao;
import bk.control.setcntrl.BookDao;
import bk.control.setcntrl.ListConnector;


public class TestMain {

    public static void main (String[] args) {

        FileManager fm = new FileManager();
        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();
        ListConnector lc = new ListConnector();




        aDao.importData(fm.readCSVFileAsObjects(FileManager.AUTHOR_TABLE_PATH));
        bDao.importData(fm.readCSVFileAsObjects(FileManager.BOOK_TABLE_PATH));

        lc.setBookMap();
        System.out.println(lc.getBookMap());

        lc.setAuthorMap();
        System.out.println(lc.getAuthorMap());

        System.out.println(lc.getBookMap().get(15));
        System.out.println(lc.getAuthorMap().get(15));
        for (Integer key : lc.getBookMap().keySet()) {
            System.out.println(key);
        }


        fm.writeBookMapToCSV(lc.getBookMap(),"mapTest.csv", FileManager.BOOK_TABLE_HEADER_ROW);
        fm.writeAuthorMapToCSV(lc.getAuthorMap(), "mapTestAuthor.csv", FileManager.AUTHOR_TABLE_HEADER_ROW);





//
//        String s = "anna";
//
//        System.out.println(s.hashCode());
//
//
//        System.out.println((int) s.charAt(0)* Math.pow(31,3)+ (int) s.charAt(1)* Math.pow(31,2)+ (int) s.charAt(2)* Math.pow(31,1)+ (int) s.charAt(3)* Math.pow(31,0));
//        System.out.println((int) s.charAt(1)* Math.pow(31,2));
//        System.out.println((int) s.charAt(2)* Math.pow(31,1));
//        System.out.println((int) s.charAt(3)* Math.pow(31,0));

        //Hascode function for string:
        String name = "Lukas";
        char[] s = new char[name.length()];
        int code = 0;
        for (int i = 0; i < name.length(); i++) {
            s[i] = name.charAt(i);
            code += (s[i]*Math.pow(31,(name.length()-1)-i));
        }
        System.out.println(code);

        System.out.println("Lukas".hashCode());

        System.out.println(getHashCode("Lukas"));
//
//
//        Author a1 = new Author.Builder()
//                .setFirstName("a")
//                .setLastName("b")
//                .build();
//
//        Author a2 = new Author.Builder()
//                .setFirstName("a")
//                .setLastName("b")
//                .build();
//
//        System.out.println(a1.hashCode());
//        System.out.println(a2.hashCode());


//        String habibi = "habibi";
//        System.out.println(getHashCode(habibi));
//
//        System.out.println(habibi.hashCode());

//
//        System.out.println(aDao.getAll().stream()
//                .filter(x -> x.getLastName().equalsIgnoreCase("Mehmet")) //filter by firstName
//                .collect(Collectors.toList()));
//        System.out.println(bDao.getAll().stream().filter(x -> x.getAID() == 1).collect(Collectors.toList())); //filter by ID
//
        ScanManager sm = new ScanManager();


        //bDao.addNew("Uzun", "Mehmet", "11441-2322-777", "Babamin kitabi", "Uzun Verlag", 1990);



        //fm.writeObjectFileCSV(bDao.exportData(),"BookTest.csv", FileManager.bookTableHeader);

        //fm.writeObjectFileCSV(aDao.exportData(), "AuthorTest.csv", FileManager.authorTableHeader);
        //algorithm

    }

    public static int getHashCode(String string) {
        //Hascode function for string:
        //s[0]*31^(n-1) + s[1]*31^(n-2) + ... + s[n-1]
        char[] s = new char[string.length()];
        int code = 0;
        for (int i = 0; i < string.length(); i++) {
            s[i] = string.charAt(i);
            code += (s[i] * Math.pow(31, (string.length() - 1) - i));
        } return code;
    }

//    public static void chooseElementFromList (String input) {
//        ScanManager sm = new ScanManager();
//        AuthorDao aDao = new AuthorDao();
//        List<Object> searchedList = new ArrayList<>(aDao.searchFor(input));
//
//        for (int i = 0; i < searchedList.size(); i++) {
//            System.out.println("    ["+(i+1)+"] "+ searchedList.get(i));
//        }
//        System.out.println("Please chose one:");
//        sm.setScanInputNum();
//
//        if (sm.getScanInputNum() > 0 || sm.getScanInputNum() <= searchedList.size()) {
//            System.out.println(searchedList.get(sm.getScanInputNum() - 1));
//        }
//
//        else {
//            System.out.println("Wrong number.");
//        }
//    }


}
