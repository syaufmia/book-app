package sum.ike.control;


import sum.ike.control.connector.db.DbManager;
import sum.ike.model.Author;

public class TestMain {


    public static void main(String[] args){

        Author author1 = new Author.Builder().setAuthorID(36).setFirstName("WERNER").setLastName("SIEMENS").build();

        AuthorDao aDao = new AuthorDao();
        BookDao bDao = new BookDao();
        DbManager sc = new DbManager();

        sc.selectAll(DbManager.Table.AUTHOR);
        System.out.println(aDao.getLastAuthor());
        aDao.addNew(author1.getFirstName(), author1.getLastName());
        System.out.println(aDao.getLastAuthor());


        sc.insertInDB(aDao.getLastAuthor());



        System.out.println(bDao.getAll());


//
//        AuthorDao aDao = new AuthorDao();
//        DbConnector db = new DbConnector();
//        Connection con = db.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
//        Statement state = null;
//        try {
//            state = con.createStatement();
//        ResultSet result = state.executeQuery("SELECT * FROM author");
//
//        while (result.next()) {
//            aDao.getData(result);
//        }
//
//        result.close();
//        state.close();
//        con.close();
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        for (Author author : aDao.getAll()) {
//            System.out.println(author);
//        }




//        User user = new User();
//        UserDao uDao = new UserDao();
//
//        String username = "syaufmia";
//
//
//        char[] s = new char[username.length()];
//        int code = 0;
//        for (int i = 0; i < username.length(); i++) {
//            s[i] = username.charAt(i);
//            code += (s[i]*Math.pow(3,(username.length()-1)-i));
//        }
//
//        System.out.println(Integer.toHexString(code));
    }

}
