// Transaction 2: insert an employee into the database

import java.util.Scanner;
import java.sql.*;

class Trans2 {
    private int eid;
    private String name;
    private int salary;
    private int sid;

    Trans2(Scanner s) {
        if (s.hasNextInt()) { eid = s.nextInt(); }
        else { eid = -1; }

        if (s.hasNext()) { name = s.next(); }
        else { name = null; }

        if (s.hasNextInt()) { salary = s.nextInt(); }
        else { salary = -1; }

        if (s.hasNextInt()) { sid = s.nextInt(); }
        else { sid = -1; }
    }

    void insertEmployee(Statement s){
        String employeeQ = "INSERT IGNORE INTO employee VALUES (" + eid + ", '" + name + "', " + salary + ")";

        try {
            s.executeUpdate(employeeQ);
            System.out.println("Done inserting " + eid + " into Employee");

            Trans3 t3 = new Trans3(eid, sid);
            t3.insertSupervisor(s);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error");
        }
    }
}
