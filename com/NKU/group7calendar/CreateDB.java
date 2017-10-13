package group7calendar;

import java.sql.*;

public class CreateDB {
    private static Connection con;
    private static boolean hasdb = false;


    public ResultSet hasConnection() throws SQLException, ClassNotFoundException {
        if (con == null) {
            getConnection();
        }
        Statement state = con.createStatement();
        ResultSet res = state.executeQuery("SELECT calendarEvent FROM eventTable");
        return res;
    }
    private void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:calendardb.db");
        initialize();
    }

    private void initialize() throws SQLException {
        if (!hasdb) {
            hasdb = true;

            Statement state = con.createStatement();
            ResultSet res = state.executeQuery("SELECT name FROM sqlite_master WHERE type = 'table' AND name = 'eventTable'");
            if (!res.next()) {
                System.out.println("Creating db");

                Statement state2 = con.createStatement();
                state2.execute("CREATE TABLE eventTable(id integer,"
                            //+ "hourkey integer,"
                            //+ "minutekey integer,"
                            + "calendarEvent varchar(100),"
                            + "primary key(id));");

                PreparedStatement prep = con.prepareStatement("INSERT INTO eventTable values(?,?);");
                prep.setString(2, "testEvent");
                prep.execute();
            }
        }
    }
}
