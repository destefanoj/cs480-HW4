// Transaction 5: Find all employees working under a supervisor
// Transaction 6: Find the average salary of employees working under a supervisor

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.*;

class Trans5And6 {
    private int sid;
    private List<Integer> employeeIDs;
    private List<String> employeeNames;
    private List<Integer> salaries;

    Trans5And6(Scanner s) {
        if (s.hasNextInt()) { sid = s.nextInt(); }
        else { sid = -1; }

        employeeIDs = new ArrayList<>();
        employeeNames = new ArrayList<>();
        salaries = new ArrayList<>();
    }

    private List<Integer> helper(Statement s, int id) throws SQLException {
        List<Integer> l = new ArrayList<>();
        String query = "SELECT e.eid "
                       + "FROM employee as e, supervisor as s "
                       + "WHERE e.eid = s.eid and "
                       + "s.sid = " + id + ";";

        ResultSet rs = s.executeQuery(query);

        while (rs.next()){
            Integer i = rs.getInt("eid");
            l.add(i);
        }

        return l;
    }

    private void getIDs (Statement s, int id) throws SQLException {
        List<Integer> l = helper(s, id);

        if (l.isEmpty()) { return; }

        for (Integer i : l) {
            getIDs(s, i);
        }

        employeeIDs.addAll(l);
    }

    private void createNamesList(Statement s) throws SQLException {
        String query, name;

        for (Integer i : employeeIDs) {
            query = "SELECT e.name "
                  + "FROM employee as e "
                  + "WHERE e.eid = " + i;

            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                name = rs.getString("e.name");
                employeeNames.add(name);
            }
        }
    }

    void supervisorEmployeeList (Statement s) throws SQLException {
        if (sid == -1) {
            System.out.println("Error: No SID given.");
            return;
        }

        getIDs(s, sid);
        createNamesList(s);

        if (employeeNames.isEmpty()) {
            System.out.println("No supervisors matching " + sid + " to find employees");
            return;
        }

        System.out.print("Supervisor Employee List: ");
        for (String n : employeeNames) {
            System.out.print(n + " ");
        }

        System.out.println();
    }

    private void average (Statement s) throws SQLException {
        String query;

        for (Integer i : employeeIDs) {
            query = "SELECT e.salary "
                    + "FROM employee as e "
                    + "WHERE e.eid = " + i;

            ResultSet rs = s.executeQuery(query);

            while (rs.next()) {
                Integer salary = rs.getInt("e.salary");
                salaries.add(salary);
            }
        }
    }

    void averageSalary (Statement s) throws SQLException {
        if (sid == -1) {
            System.out.println("Error: No SID given.");
            return;
        }

        getIDs(s, sid);
        average(s);

        if (salaries.isEmpty()) {
            System.out.println("No supervisors matching " + sid + " to find salaries");
            return;
        }

        int n = salaries.size();
        int total = 0;

        for (Integer i : salaries) {
            total += i;
        }

        int average = total / n;

        System.out.println("Average salary: $" + average);
    }
}
