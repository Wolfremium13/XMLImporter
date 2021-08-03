import models.Company;
import models.Staff;

import java.sql.*;

public class PostgresConnection {


    public void insertCompany(Company company) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres")) {
            final int companyId = getResultSetFromCompany(company, conn);
            for (Staff staff : company.staff) {
                try {
                    insertStaff(conn, companyId, staff);
                    insertSalary(conn, staff);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearTables() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/postgres", "postgres", "postgres")) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(
                    "DELETE FROM salary; DELETE FROM staff; DELETE FROM company")) {
                preparedStatement.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void insertSalary(Connection conn, Staff staff) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO salary(staff_id, currency, value) VALUES (?,?,?)")) {
            preparedStatement.setInt(1, staff.id);
            preparedStatement.setString(2, staff.salary.currency);
            preparedStatement.setInt(3, staff.salary.value);
            preparedStatement.executeUpdate();
        }
    }

    private void insertStaff(Connection conn, int companyId, Staff staff) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement(
                "INSERT INTO staff(id,company_id, first_name, last_name, nick_name) VALUES (?,?,?,?,?)")) {
            preparedStatement.setInt(1, staff.id);
            preparedStatement.setInt(2, companyId);
            preparedStatement.setString(3, staff.firstname);
            preparedStatement.setString(4, staff.lastname);
            preparedStatement.setString(5, staff.nickname);
            preparedStatement.executeUpdate();
        }
    }

    private int getResultSetFromCompany(Company company, Connection conn) throws SQLException {
        try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO company(name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, company.name);
            preparedStatement.executeUpdate();
            return getCompanyId(preparedStatement);
        }
    }

    private int getCompanyId(Statement statement) throws SQLException {
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return (int) generatedKeys.getLong(1);
            } else throw new SQLException("No ID obtained.");
        }
    }
}
