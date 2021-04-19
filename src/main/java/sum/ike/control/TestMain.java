package sum.ike.control;

import sum.ike.control.db.DbConnector;
import sum.ike.control.db.DbManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class TestMain {


    public static void main(String[] args){

        LocalDateTime date2 = LocalDateTime.of(LocalDate.now(), LocalTime.of(14,0, 0));
        LocalDateTime date = LocalDateTime.now(Clock.systemUTC());
        DbConnector dbc = new DbConnector();
        Connection con = dbc.connect(DbConnector.BIB_URL, DbConnector.BIB_USER, DbConnector.BIB_PASS);
        Statement state;
        try {
            state = con.createStatement();
            state.executeUpdate("INSERT INTO test (time) VALUES ('"
                    + date2
                    + "');");
            state.close();
            con.close();
        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Doppelter Eintrag wurde ignoriert. " + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        LocalDate.now();

        System.out.println(date2);

        System.out.println(date.getHour());
        System.out.println(date.getMinute());

    }

}
