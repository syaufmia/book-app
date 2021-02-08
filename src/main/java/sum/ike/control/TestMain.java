package sum.ike.control;

public class TestMain {


    public static void main(String[] args) {
        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();

        FileManager fm = new FileManager();

        aDao.importData(fm.readCSVFileAsObjects("AuthorList.csv"));
        fm.writeObjectFileJson(aDao.exportData(),"authors.json");

    }
}
