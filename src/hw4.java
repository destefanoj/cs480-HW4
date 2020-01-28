import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;
import java.util.Scanner;

public class hw4 {

    private static final String CREATE_EMPLOYEE_TABLE =
                                "CREATE TABLE employee ("
                                + "eid integer,"
                                + "name varchar(20),"
                                + "salary integer,"
                                + "PRIMARY KEY (eid))";

    private static final String CREATE_SUPERVISOR_TABLE =
            "CREATE TABLE supervisor ("
                    + "eid integer,"
                    + "sid integer,"
                    + "PRIMARY KEY (eid),"
                    + "FOREIGN KEY (eid) REFERENCES employee (eid)"
                    + "on delete cascade)";

    public static void main (String[] args){
        String url = "jdbc:mysql://localhost:3306/homework4db";
        String username = "jdeste3";
        String password = "password";
        String filePath = "/Users/jessicadestefano/Desktop/CS480-HW4/src/transfile.txt";

        Statement stmt;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Connected to database!\n");
            stmt = connection.createStatement();

            addTables(stmt);
            System.out.println("Employee and Supervisor tables added\n");

            // Read in the file
            Scanner s = null;
            String line;

            try {
                File f = new File(filePath);
                s = new Scanner (f);
            } catch (FileNotFoundException e){
                System.err.println("transfile.txt not found");
                System.exit(-3);
            }

            // Parse the transactions
            while ((line = FileScanner.getLine(s)) != null) {
                Scanner lineScanner = new Scanner(line);
                int transaction = lineScanner.nextInt();

                switch (transaction) {
                    case 1:
                        Trans1 t1 = new Trans1(lineScanner);
                        t1.deleteEmployee(stmt);
                        break;
                    case 2:
                        Trans2 t2 = new Trans2(lineScanner);
                        t2.insertEmployee(stmt);
                        break;
                    case 3:
                        Trans3 t3 = new Trans3(lineScanner);
                        t3.insertSupervisor(stmt);
                        break;
                    case 4:
                        Trans4 t4 = new Trans4();
                        t4.averageEmployee(stmt);
                        break;
                    case 5:
                        Trans5And6 t5 = new Trans5And6(lineScanner);
                        t5.supervisorEmployeeList(stmt);
                        break;
                    case 6:
                        Trans5And6 t6 = new Trans5And6(lineScanner);
                        t6.averageSalary(stmt);
                        break;
                    default:
                        System.err.println("Error: Transaction Unknown");
                        break;
                }
            }

            dropTables(stmt);
            System.out.println("\nEmployee and Supervisor tables dropped");

            stmt.close();
            connection.close();
            s.close();

        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    private static void addTables(Statement s) throws SQLException {
        s.executeUpdate(CREATE_EMPLOYEE_TABLE);
        s.executeUpdate(CREATE_SUPERVISOR_TABLE);
    }

    private static void dropTables(Statement s) throws SQLException {
        s.executeUpdate("DROP TABLE supervisor");
        s.executeUpdate("DROP TABLE employee");
    }

    static boolean isEmployee(Statement s, int id) throws SQLException {
        String query = "SELECT e.eid FROM employee as e WHERE e.eid = " + id;

        int count = 0;
        ResultSet rs = s.executeQuery(query);
        while (rs.next()) {
            if (rs.getInt("eid") == id) { count++; }
        }

        return count == 1;
    }
}

