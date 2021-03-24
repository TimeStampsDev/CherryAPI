package net.cherryflavor.api.database;

import net.cherryflavor.api.configuration.CherryConfig;
import net.cherryflavor.api.tools.TextFormat;

import java.sql.*;
import java.util.Properties;

/**
 * Created on 2/20/2021
 * Time 12:32 AM
 */
public class Database {

    private String name, host, port, database, username, password;
    private static Connection connection;

    private CherryConfig cherryConfig;

    //==================================================================================================================
    // CONSTRUCTORS
    //==================================================================================================================

    /**
     * @param name
     * @param host
     * @param port
     * @param database
     * @param username
     * @param password
     */
    public Database(String name, String host, String port, String database, String username, String password) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;


        this.cherryConfig = new CherryConfig(name.toLowerCase() + ".yml");
    }

    //==================================================================================================================
    // GETTERS
    //==================================================================================================================

    /**
     * @return config for database
     */
    public CherryConfig getCherryConfig() {
        return this.cherryConfig;
    }

    /** Name of Database (Identifier) */
    protected String getActualName() { return this.name; }
    protected String getHost() { return this.host; }
    protected String getPort() { return this.port; }
    protected Integer getPortAsInteger() { return Integer.valueOf(this.port); }

    /** Name of database being accessed */
    protected String getDatabaseName() { return this.database; }

    protected String getUsername() { return this.username; }
    protected String getPassword() { return this.password; }

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

    //==================================================================================================================
    // METHODS
    //==================================================================================================================

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

    /**
     * This method executes SQL query 'INSERT'
     * <br> 'INSERT INTO database ' + insert
     * @apiNote
     * <p>'INSERT INTO database' <u>NOT NEEDED</u>
     * <br> ';' <u>NOT NEEDED</u></p>
     * @param insert
     * @param into
     * @param values
     */
    public void INSERT(String insert, String[] into, String[] values) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO " + database + " (" + TextFormat.stripOutliers(into.toString()) + ") VALUES (" + TextFormat.stripOutliers(values.toString()) + ");");
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

}
