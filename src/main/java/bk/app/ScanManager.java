package bk.app;

import java.util.Scanner;

public class ScanManager {

    Scanner scan = new Scanner(System.in);

    private  String scanIsbn;
    private  String scanTitle;
    private  String scanFirst;
    private  String scanLast;
    private  String scanPublisher;
    private  int scanYear;
    private  int scanInputNum;

    public void setScanInputNum() {
        String sin = scan.nextLine();
        while (!sin.matches("\\d")) {
            System.out.println("Please enter a correct value");
            sin = scan.nextLine();
        }
        scanInputNum = Integer.parseInt(sin);
    }

    public void setScanIsbn () {
        scanIsbn = scan.nextLine();
    }

    public void setScanTitle () {
        scanTitle = scan.nextLine();
    }

    public void setScanFirst () {
        scanFirst = scan.nextLine();
    }

    public void setScanLast () {
        scanLast = scan.nextLine();
    }

    public void setScanPublisher () {
        scanPublisher = scan.nextLine();
    }

    public void setScanYear () {
        String sy = scan.nextLine();
        if (sy.matches("\\d{4}")) {
            scanYear = Integer.parseInt(sy);
        }
        else {
            scanYear = 2000;
        }
    }

    public int getScanInputNum() {
        return scanInputNum;
    }

    public String getScanIsbn () {
        return scanIsbn;
    }

    public String getScanTitle () {
        return scanTitle;
    }

    public String getScanFirst () {
        return scanFirst;
    }

    public String getScanLast () {
        return scanLast;
    }

    public String getScanPublisher () {
        return scanPublisher;
    }

    public int getScanYear () {
        return scanYear;
    }

}
