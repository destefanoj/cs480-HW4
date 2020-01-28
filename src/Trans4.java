// Transaction 4: Find the average employee salary

import java.sql.*;

class Trans4 {

    Trans4() {
    }

    void averageEmployee(Statement s) {
        String query = "SELECT avg (salary) as avg FROM employee";
        int average = -1;
        ResultSet rs;

        try {
            rs = s.executeQuery(query);

            while (rs.next()) {
                average = rs.getInt("avg");
            }

            System.out.println("Average employee salary: $" + average);

        } catch (SQLException e) {
            System.err.println("Error occurred");
            e.printStackTrace();
        }
    }
}
