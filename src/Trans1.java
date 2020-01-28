// Transaction 1: Delete an employee

import java.util.Scanner;
import java.sql.*;
import static java.sql.JDBCType.NULL;

class Trans1 {

    private int eid;

    Trans1(Scanner s) {
        if (s.hasNextInt()) { eid = s.nextInt(); }
        else { eid = -1; }
    }

    private String delete () {
       return "DELETE FROM employee as e WHERE e.eid = " + eid;
    }

    private void updateSupervisor (Statement s) throws SQLException {
        String query = "UPDATE supervisor SET sid = " + NULL + " WHERE sid = " + eid;
        s.executeUpdate(query);
    }

    void deleteEmployee (Statement s) throws SQLException {
        String error;
        String query;

        if (eid == -1) {
            error = "Error: No employee entered to delete";
        }
        else if (!hw4.isEmployee(s, eid)) {
            error = "Error: Employee not found in employee table";
        }
        else {
            query = delete();
            s.executeUpdate(query);
            updateSupervisor(s);
            error = "Done deleting employee " + eid;
        }

        print(error);
    }

    private void print(String s) {
        System.out.println(s);
    }
}
