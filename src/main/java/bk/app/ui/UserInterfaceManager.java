package bk.app.ui;

import bk.app.ScanManager;
import bk.control.setcntrl.AuthorDao;
import bk.control.setcntrl.BookDao;
import bk.set.Author;
import bk.set.Book;

import java.util.ArrayList;
import java.util.List;

public class UserInterfaceManager {

    BookDao bDao = new BookDao();
    AuthorDao aDao = new AuthorDao();
    ScanManager sm = new ScanManager();


    public void printListSection () {
        System.out.println("Select the category by which you want to sort the list");
        System.out.println();
        System.out.println("========================================");
        System.out.println("||    [1] List of Books               ||");
        System.out.println("||    [2] List of Author              ||");
        System.out.println("||    [9] RETURN TO MAIN              ||");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Type your choice: ");
        sm.setScanInputNum();
        boolean exit = false;
        while (!exit) {
            switch (sm.getScanInputNum()) {
                case 1:
                    bDao.getAll().sort(new Book.BookIDComparator());
                    for (Book book : bDao.getAll()) {
                        System.out.println(aDao.getAuthorByID(book.getAuthorID()).toStringNoID() + " - " + book.toStringNoID());
                    }
                    exit = true;
                    break;
                case 2:
                    aDao.getAll().sort(new Author.AuthorIDComparator());
                    for (Author author : aDao.getAll()) {
                        System.out.println(author);
                    }
                    exit = true;
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a correct number.");
                    sm.setScanInputNum();
            }
        }
    }

    //TODO: REFACTOR!!!!!!!!!!!!!!
    public void addBookSection () {
        System.out.println("========================================");
        System.out.println("||        ADDING A NEW BOOK           ||");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Enter the Title: ");
        sm.setScanTitle();
        System.out.println("Enter ISBN: ");
        sm.setScanIsbn();
        System.out.println("Enter the publisher: ");
        sm.setScanPublisher();
        System.out.println("Enter the year of publishing: ");
        sm.setScanYear();
        System.out.println("========================================");
        System.out.println("||            CHECK THE AUTHOR        ||");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Enter a name: ");
        sm.setScanLast();
        List<Author> list = aDao.searchFor(sm.getScanLast());
        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                System.out.println("    [" + (i + 1) + "] " + list.get(i));
            }
            System.out.println("    [" + (list.size() + 1) + "] NONE OF THE ABOVE.");
            System.out.println("Choose:");
            sm.setScanInputNum();
            boolean exitThisSection = false;
            while (!exitThisSection) {
                if (sm.getScanInputNum() > 0 && (sm.getScanInputNum() <= list.size())) {
                    exitThisSection = true;
                    Author author = aDao.select(sm.getScanInputNum(), list);
                    bDao.addNew(author.getAuthorID(),
                            sm.getScanIsbn(),
                            sm.getScanTitle(),
                            sm.getScanPublisher(),
                            sm.getScanYear());
                } else if (sm.getScanInputNum() == (list.size() + 1)) {
                    exitThisSection = true;
                    System.out.println("You choose to add your own Author.");
                    addAuthorSection();
                } else {
                    System.out.println("Please enter a correct number!");
                }
            }
        } else {
            System.out.println("No such Author found. Add your new Author.");
            addAuthorSection();
        }
    }

    public void addAuthorSection () {
        System.out.println("========================================");
        System.out.println("||        ADDING A NEW AUTHOR         ||");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Enter First name of Author: ");
        sm.setScanFirst();
        System.out.println("Enter Last Name of Author: ");
        sm.setScanLast();
        bDao.addNew(sm.getScanFirst(),
                sm.getScanLast(),
                sm.getScanIsbn(),
                sm.getScanTitle(),
                sm.getScanPublisher(),
                sm.getScanYear());
    }

    public void newDeleteSection () {
        boolean exit = false;
        while (!exit) {
            System.out.println("What do you want to delete?");
            System.out.println();
            System.out.println("========================================");
            System.out.println("||    [1] a Book                      ||");
            System.out.println("||    [2] an Author                   ||");
            System.out.println("||    [9] RETURN TO MAIN              ||");
            System.out.println("========================================");
            System.out.println();
            System.out.println("Type your choice: ");
            sm.setScanInputNum();
            switch (sm.getScanInputNum()) {
                case 1:
                    System.out.println("Select the category to you want to search for");
                    System.out.println();
                    System.out.println("========================================");
                    System.out.println("||    [1] ISBN                        ||");
                    System.out.println("||    [2] Title                       ||");
                    System.out.println("||    [3] Publisher                   ||");
                    System.out.println("||    [9] RETURN                      ||");
                    System.out.println("========================================");
                    System.out.println();
                    System.out.println("Type your choice: ");
                    sm.setScanInputNum();
                    boolean exit2 = false;
                    while (!exit2) {
                        switch (sm.getScanInputNum()) {
                            case 1:
                                System.out.println("Enter the ISBN: ");
                                sm.setScanIsbn();
                                System.out.println(bDao.getFilteredList(sm.getScanIsbn(), Book.Attribute.ISBN));
                                exit2 = true;
                                break;
                            case 2:
                                System.out.println("Enter the title");
                                sm.setScanTitle();
                                System.out.println(bDao.getFilteredList(sm.getScanTitle(), Book.Attribute.TITLE));
                                exit2 = true;
                                break;
                            case 3:
                                System.out.println("Enter the publisher");
                                sm.setScanPublisher();
                                System.out.println(bDao.getFilteredList(sm.getScanPublisher(), Book.Attribute.PUBLISHER).toString());
                                exit2 = true;
                                break;
                            case 9:
                                exit2 = true;
                                break;
                            default:
                                System.out.println("Please enter a correct number.");
                                sm.setScanInputNum();
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter a name: ");
                    sm.setScanLast();
                    List<Author> list = aDao.searchFor(sm.getScanLast());
                    if (!list.isEmpty()) {
                        for (int i = 0; i < list.size(); i++) {
                            System.out.println("    [" + (i + 1) + "] " + list.get(i));
                        }
                        System.out.println("    [" + (list.size() + 1) + "] RETURN.");
                        System.out.println("Choose: ");
                        System.out.println("CAUTION! The selected one will be deleted!");
                        sm.setScanInputNum();
                        boolean exitThisSection = false;
                        while (!exitThisSection) {
                            if (sm.getScanInputNum() > 0 && (sm.getScanInputNum() <= list.size())) {
                                exitThisSection = true;
                                aDao.delete(aDao.select(sm.getScanInputNum(), list));  //DELETE AUTHOR AND ALL BOOKS
                            } else if (sm.getScanInputNum() == (list.size() + 1)) {
                                exitThisSection = true;
                            } else {
                                System.out.println("Please enter a correct number!");
                            }
                        }

                    } else {
                        System.out.println("No such Author found. Add your new Author.");
                        addAuthorSection();
                        break;
                    }
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a correct number.");
                    sm.setScanInputNum();

            }
        }
    }



    public  void deleteSelection () {
        boolean exit = false;
        System.out.println("Select the category to you want to look for");
        System.out.println();
        System.out.println("========================================");
        System.out.println("||    [1] ISBN                        ||");
        System.out.println("||    [2] Author                      ||");
        System.out.println("||    [3] Title                       ||");
        System.out.println("||    [4] Publisher                   ||");
        System.out.println("||    [9] RETURN TO MAIN              ||");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Type your choice: ");
        sm.setScanInputNum();
        System.out.println("CAUTION! The book will be deleted.");
        while (!exit) {
            switch (sm.getScanInputNum()) {
                case 1:
                    System.out.println("Enter the ISBN: ");
                    sm.setScanIsbn();
                    bDao.delete(sm.getScanIsbn(), Book.Attribute.ISBN);
                    exit = true;
                    break;
                case 2:
                    boolean exit2 = false;
                    System.out.println("========================================");
                    System.out.println("||    [1] First name                  ||");
                    System.out.println("||    [2] Last name                   ||");
                    System.out.println("||    [9] RETURN                      ||");
                    System.out.println("========================================");
                    System.out.println("Type your choice: ");
                    sm.setScanInputNum();
                    while (!exit2) {
                        if (sm.getScanInputNum() == 1) {
                            System.out.println("Enter the First name: ");
                            sm.setScanFirst();
                         //   bDao.delete(sm.getScanFirst(), Book.BookField.FIRST_NAME);
                            //TODO: ConcurrentModificationException!!! (da wir Elemente aus der Liste löschen, während wir iterieren.
                            exit2 = true;
                        }
                        else if (sm.getScanInputNum() == 2) {
                            System.out.println("Enter the Last name: ");
                            sm.setScanLast();
                          //  bDao.delete(sm.getScanLast(), Book.BookField.LAST_NAME);
                            exit2 = true;
                        }
                        else if (sm.getScanInputNum() == 9) {
                            exit2 = true;
                        }
                        else {
                            System.out.println("Please enter a correct number");
                            sm.setScanInputNum();
                        }
                    }
                    exit = true;
                    break;
                case 3:
                    System.out.println("Enter the title");
                    sm.setScanTitle();
                    bDao.delete(sm.getScanTitle(), Book.Attribute.TITLE);
                    exit = true;
                    break;
                case 4:
                    System.out.println("Enter the publisher");
                    sm.setScanPublisher();
                    bDao.delete(sm.getScanPublisher(), Book.Attribute.PUBLISHER);
                    exit = true;
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a correct number.");
                    sm.setScanInputNum();
            }
        }
    }


    public void searchSection () {
        boolean exit = false;
        while (!exit) {
            System.out.println("What are you looking for?");
            System.out.println();
            System.out.println("========================================");
            System.out.println("||    [1] a Book                      ||");
            System.out.println("||    [2] an Author                   ||");
            System.out.println("||    [9] RETURN TO MAIN              ||");
            System.out.println("========================================");
            System.out.println();
            System.out.println("Type your choice: ");
            sm.setScanInputNum();
            switch (sm.getScanInputNum()) {
                case 1:
                    System.out.println("Select the category to you want to search for");
                    System.out.println();
                    System.out.println("========================================");
                    System.out.println("||    [1] ISBN                        ||");
                    System.out.println("||    [2] Title                       ||");
                    System.out.println("||    [3] Publisher                   ||");
                    System.out.println("||    [9] RETURN                      ||");
                    System.out.println("========================================");
                    System.out.println();
                    System.out.println("Type your choice: ");
                    sm.setScanInputNum();
                    boolean exit2 = false;
                    while (!exit2) {
                        switch (sm.getScanInputNum()) {
                            case 1:
                                System.out.println("Enter the ISBN: ");
                                sm.setScanIsbn();
                                System.out.println(bDao.getFilteredList(sm.getScanIsbn(), Book.Attribute.ISBN));
                                exit2 = true;
                                break;
                            case 2:
                                System.out.println("Enter the title");
                                sm.setScanTitle();
                                System.out.println(bDao.getFilteredList(sm.getScanTitle(), Book.Attribute.TITLE));
                                exit2 = true;
                                break;
                            case 3:
                                System.out.println("Enter the publisher");
                                sm.setScanPublisher();
                                System.out.println(bDao.getFilteredList(sm.getScanPublisher(), Book.Attribute.PUBLISHER).toString());
                                exit2 = true;
                                break;
                            case 9:
                                exit2 = true;
                                break;
                            default:
                                System.out.println("Please enter a correct number.");
                                sm.setScanInputNum();
                        }
                    }
                    break;
                case 2:
                    System.out.println("Enter a name: ");
                    sm.setScanLast();
                    chooseElementFromAuthorList(sm.getScanLast());
                    break;
                case 9:
                    exit = true;
                    break;
                default:
                    System.out.println("Please enter a correct number.");
                    sm.setScanInputNum();
            }
        }

    }

    public void mainSectionMenu () {
        System.out.println();
        System.out.println("========================================");
        System.out.println("||    [1] Show complete list          ||");
        System.out.println("||    [2] Add an element              ||");
        System.out.println("||    [3] Delete an element           ||");
        System.out.println("||    [4] Search for an element       ||");
        System.out.println("||    [9] EXIT                        ||");
        System.out.println("========================================");
        System.out.println();
        System.out.println("Select your choice: ");
    }


    public void chooseElementFromAuthorListToDelete (String input) {
        List<Object> searchedList = new ArrayList<>(aDao.searchFor(input));

        if (!searchedList.isEmpty()) {
            for (int i = 0; i < searchedList.size(); i++) {
                System.out.println("    [" + (i + 1) + "] " + searchedList.get(i));
            }
            System.out.println("    [" + (searchedList.size() + 1) + "] NONE OF THE ABOVE.");
            System.out.println("Please choose one:");
            sm.setScanInputNum();
            boolean goBack = false;
            while (!goBack) {
                if (sm.getScanInputNum() > 0 || sm.getScanInputNum() <= searchedList.size()) {
                    System.out.println(searchedList.get(sm.getScanInputNum() - 1));
                    goBack = true;
                } else if (sm.getScanInputNum() == (searchedList.size() + 1)) {
                    goBack = true;
                } else {
                    System.out.println("Incorrect input");
                }
            }
        }
        else {
            System.out.println("Author doesn't exist.");
        }
    }

    public void chooseElementFromAuthorList (String input) {
        List<Object> searchedList = new ArrayList<>(aDao.searchFor(input));

        if (!searchedList.isEmpty()) {
            for (int i = 0; i < searchedList.size(); i++) {
                System.out.println("    [" + (i + 1) + "] " + searchedList.get(i));
            }
            System.out.println("    [" + (searchedList.size() + 1) + "] NONE OF THE ABOVE.");
            System.out.println("Please choose one:");
            sm.setScanInputNum();
            boolean goBack = false;
            while (!goBack) {
                if (sm.getScanInputNum() > 0 || sm.getScanInputNum() <= searchedList.size()) {
                    System.out.println(searchedList.get(sm.getScanInputNum() - 1));
                    goBack = true;
                } else if (sm.getScanInputNum() == (searchedList.size() + 1)) {
                    goBack = true;
                } else {
                    System.out.println("Incorrect input");
                }
            }
        }
        else {
            System.out.println("Author doesn't exist.");
        }
    }
}
