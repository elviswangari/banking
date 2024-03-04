package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
    public static Connection connect() {
        String url = "jdbc:mysql://localhost:3306/bank";
        String username = "test";
        String password = "password";
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Customer getCustomer(String username) {
        String sql = "SELECT * FROM customers WHERE username = ?";
        Customer customer = null;

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet resultSet = statement.executeQuery()) {
                // Check if the ResultSet contains any rows
                if (resultSet.next()) {
                    // Move the cursor to the first row
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    String password = resultSet.getString("password");
                    int accountId = resultSet.getInt("accountId");

                    // Create a new Customer object with the retrieved data
                    customer = new Customer(id, name, username, password, accountId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static Accounts getAccount(int accountId) {
        String sql = "select * from accounts where id = ?";
        Accounts account = null;

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, accountId);

            try (ResultSet resultSet = statement.executeQuery();) {
                account = new Accounts(
                        resultSet.getInt("id"),
                        resultSet.getString("type"),
                        resultSet.getDouble("balance"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public static void updateAccountBalance(int accountId, double balance) {
        String sql = "update accounts set balance = ? where id = ?";

        try (Connection connection = connect();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, balance);
            statement.setInt(2, accountId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // public static void main(String[] args) {
    // Customer customer = getCustomer("elviso");
    // System.out.println(customer.getName());

    // Accounts account = getAccount(customer.getAccountId());
    // System.out.println(account.getBalance());
    // }
}
