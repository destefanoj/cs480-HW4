// Transaction 3: Insert a supervisor

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static java.sql.JDBCType.NULL;

class Trans3 {
    private int eid;
    private int sid;

    Trans3(Scanner s) {
        if (s.hasNextInt()) { eid = s.nextInt(); }
        else { eid = -1; }

        if (s.hasNextInt()) { sid = s.nextInt(); }
        else { sid = -1; }
    }

    Trans3(int e, int s) {
        eid = e;
        sid = s;
    }

    private String insertNotNull() {
        return "INSERT INTO supervisor VALUES (" + eid + ", " + sid + ") " +
                "ON DUPLICATE KEY UPDATE supervisor.sid = " + sid;
    }

    private String insertNull() {
        return "INSERT INTO supervisor VALUES (" + eid + ", " + NULL + ") " +
                "ON DUPLICATE KEY UPDATE supervisor.sid = " + NULL;
    }

    void insertSupervisor(Statement s) throws SQLException {
        String query;
        String error;

        if (sid == -1) {
            query = insertNull();
            error = "Error: No SID entered, entering NULL";
        }
        else if (!hw4.isEmployee(s, sid)) {
            query = insertNull();
            error = "Error: Supervisor not an employee, entering NULL";
        } else {
            query = insertNotNull();
            error = "Done inserting " + sid + " into Supervisor";
        }

        s.executeUpdate(query);
        print(error);
    }

    private void print(String s) {
        System.out.println(s);
    }

}
