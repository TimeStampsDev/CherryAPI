package net.cherryflavor.api.database;

import net.cherryflavor.api.configuration.CherryConfig;

import java.sql.*;
import java.util.Properties;

public class Database {

    private String name, host, port, database, username, password;
    private static Connection connection;

    private CherryConfig cherryConfig;

    public Database(String name, String host, String port, String database, String username, String password) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;


        this.cherryConfig = new CherryConfig(name.toLowerCase() + ".yml");
    }

    /**
     * @return config for database
     */
    public CherryConfig getCherryConfig() {
        return this.cherryConfig;
    }

    /**
     * Opens connection for database
     */
    public void openConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return;
            }

            Class.forName("com.mysql.jdbc.Driver");

            String url = "jdbc:mysql//" + host + ":" + port + "/" + database;
            Properties credentials = new Properties();
            credentials.put("user", username);
            credentials.put("password", password);

            connection = DriverManager.getConnection(url, credentials);

            connection.setAutoCommit(false);

            if (connection != null) {
                DatabaseManager.debug("Connection established for " + name);
            } else {
                DatabaseManager.debug("Could not establish connection for " + name);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Closes connection for database
     */
    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                DatabaseManager.debug("Connection closed for " + name);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * @param query
     * @return ResultSet
     */
    public ResultSet getResultSet(String query) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            return preparedStatement.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method executes SQL query 'INSERT'
     * <br> 'INSERT INTO database ' + insert
     * @apiNote
     * <p>'INSERT INTO database' <u>NOT NEEDED</u>
     * <br> ';' <u>NOT NEEDED</u></p>
     */
    public void INSERT(String insert) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + database + " " + insert + ";");
            preparedStatement.executeQuery();
            commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void INSERT(String insert, String[] into, String[] values) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + database + " (" + stripOutliers(into.toString()) + ") VALUES (" + stripOutliers(values.toString()) + ");");
            preparedStatement.executeQuery();
            commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Commits queries
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns string to
     * <blockquote><pre>
     * "hello, world"   to   "ello, worl" </pre></blockquote>
     * @param string
     * @return String
     */
    private String stripOutliers(String string) {
        string = string.substring(1);
        string = string.substring(0, string.length() - 1);
        return string;
    }

}
