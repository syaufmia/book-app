package bk.app.ui;

import bk.app.ScanManager;
import bk.control.setcntrl.AuthorDao;
import bk.control.setcntrl.BookDao;


public class MainMenu {

    public static class MenuSelection {
        static final int SHOW_COMPLETE = 1;
        static final int ADDING = 2;
        static final int DELETING = 3;
        static final int SEARCHING = 4;
        static final int EXIT_APP = 9;
    }

    public static void main (String[] args) {

        UserInterfaceManager uim = new UserInterfaceManager();
        FileManager fm = new FileManager();
        ScanManager sm = new ScanManager();
        BookDao bDao = new BookDao();
        AuthorDao aDao = new AuthorDao();


        aDao.importData(fm.readCSVFileAsObjects(FileManager.AUTHOR_TABLE_PATH));
        bDao.importData(fm.readCSVFileAsObjects(FileManager.BOOK_TABLE_PATH));

        aDao.setAuthorCounterToMax(); //IMPORTANT: lastly we set the counter for the ID to the max number in author list
        System.out.println(bDao.getAll());

        int selection;
        boolean exitGame = false;

        System.out.println("============================================================");
        System.out.println("||  Welcome to the Book Manager. What do you want to do?  ||");
        System.out.println("============================================================");
        uim.mainSectionMenu();
        sm.setScanInputNum();
        selection = sm.getScanInputNum();
        while (!exitGame) {
            switch (selection) {
                case MenuSelection.SHOW_COMPLETE:
                    uim.printListSection();
                    uim.mainSectionMenu();
                    sm.setScanInputNum();
                    selection = sm.getScanInputNum();
                    break;
                case MenuSelection.ADDING:
                    uim.addBookSection(); // ADDING BOOK //TODO: ADDING AUTHOR ALONE
                    uim.mainSectionMenu();
                    sm.setScanInputNum();
                    selection = sm.getScanInputNum();
                    break;
                case MenuSelection.DELETING:
                    uim.newDeleteSection();
                    uim.mainSectionMenu();
                    sm.setScanInputNum();
                    selection = sm.getScanInputNum();
                    break;
                case MenuSelection.SEARCHING:
                    uim.searchSection();   //NEW AUTHOR SEARCHING ADDED with Space //TODO: SEARCHING FOR BOOK
                    uim.mainSectionMenu();
                    sm.setScanInputNum();
                    selection = sm.getScanInputNum();
                    break;
                case MenuSelection.EXIT_APP:
                    fm.writeObjectFileCSV(bDao.exportData(),FileManager.BOOK_TABLE_PATH, FileManager.BOOK_TABLE_HEADER_ROW);
                    fm.writeObjectFileCSV(aDao.exportData(), FileManager.AUTHOR_TABLE_PATH, FileManager.AUTHOR_TABLE_HEADER_ROW);
                    System.out.println("Changes saved.");
                    System.out.println("Thanks for using the app!");
                    exitGame = true;
            }
        }
    }
}

