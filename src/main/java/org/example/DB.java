package org.example;

import javax.swing.*;
import javax.xml.crypto.dom.DOMCryptoContext;
import java.sql.*;

public class DB {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/jdbcfanap";
    static final String USER = "root";
    static final String PASS = "MohammadSaleh";

    public static void main(String[] args) throws SQLException {
//        insert();
//        select();
        create_table();
    }

    public static void create_table() throws SQLException{
        Connection connection = null;
        Statement statement = null;
        try {
            // Step 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // Step 2: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASS);

            // Step 3: Execute a query
            System.out.println("Creating table in given database...");
            statement = connection.createStatement();

            String query = "CREATE TABLE sql_test (id NUMERIC NOT NULL, str VARCHAR(20), PRIMARY KEY (id))";
            statement.executeUpdate(query);

            System.out.println("Created table in given database...");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }
    }
    public static void insert(/*String query*/) throws SQLException{
        Connection connection = null;
        Statement statement = null;
        PreparedStatement preparedStatement = null;
        try {
            // STEP 1: Register JDBC driver
            Class.forName(JDBC_DRIVER);

            // STEP 2: Open a connection
            System.out.println("Connecting to a selected database...");
            connection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Connected database successfully...");
            connection.setAutoCommit(false);

            // STEP 3: Execute a query
//            statement = connection.createStatement();
//            String sql = "INSERT INTO ACCOUNT VALUES (6, '1.3.3.4', 2000, 1)";
//            statement.executeUpdate(sql);
//            ////////////////
//            sql = "INSERT INTO ACCOUNT " + "VALUES (7, '5.3.2.1', 50, 2)";
//            statement.executeUpdate(sql);
//            System.out.println("Inserted records into the table...");
//            ////////////////////////////

            // STEP 3: Execute a query with preparedStatment
            String query = "INSERT INTO account (id, account_number, amount, owner) VALUES (?,?,?,?)";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, 8);
            preparedStatement.setString(2, "3.3.1.4");
            preparedStatement.setDouble(3, 200.0);
            preparedStatement.setLong(4, 1);
            preparedStatement.executeUpdate();
            System.out.println("Inserted records into the table...");

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (connection != null)
                connection.rollback();

        } finally {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }
    }
    public static void select() throws SQLException{
        Connection connection = null;
        Statement statement = null;

        try {
            // Step1: Register JDBC Driver
            Class.forName(JDBC_DRIVER);

            // Step2: Open a connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASS);

            // Step3: Execute a query
            System.out.println("Connected database successfully...");
            statement = connection.createStatement();
            String query = "SELECT ID, ACCOUNT_NUMBER, AMOUNT FROM ACCOUNT";
            ResultSet resultSet = statement.executeQuery(query);

            // Step4: Extract data from result set
            while (resultSet.next()){
                //Retrieve by column name
                int id = resultSet.getInt("ID");
                String accountNumber = resultSet.getNString("ACCOUNT_NUMBER");
                double amount = resultSet.getDouble("AMOUNT");

                //Display values
                System.out.println(" id: " + id +
                        "\n accountNumber: " + accountNumber +
                        "\n amount: " + amount +"\n");
            }

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();
        }
    }
}
