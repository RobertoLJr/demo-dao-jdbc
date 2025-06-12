package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DB {
    private static Connection conn = null;

    /**
     * Establishes and returns a singleton database connection using JDBC.
     * <p>
     *     If the connection has not been initialized yet, this method loads the database properties
     *     from a configuration file, retrieves the database URL, and attempts to establish a new connection.
     *     On subsequent calls, the existing connection instance is returned.
     * </p>
     *
     * @return the {@link Connection} instance to the database
     * @throws DbException if a database access error occurs during the connection attempt
     */
    public static Connection getConnection() {
        if (conn == null) {
            try {
                Properties properties = loadProperties();
                String url = properties.getProperty("dburl");
                conn = DriverManager.getConnection(url, properties);
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }

        return conn;
    }

    /**
     * Closes the existing database connection if it is open.
     * <p>
     *     This method checks if a connection has been previously established and,
     *     if so, attempts to close it. If a {@link SQLException} occurs during the
     *     process, it is wrapped and rethrown as a {@link DbException}.
     * </p>
     *
     * @throws DbException if an error occurs while closing the connection
     */
    public static void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Loads database configuration properties from the configuration file.
     * <p>
     *     This method reads key-value pairs from a properties file located in the root
     *     directory of the application and returns them as a {@link Properties} object.
     *     If an I/O error occurs during reading, a {@link DbException} is thrown.
     * </p>
     *
     * @return a {@link Properties} object containing the database configuration
     * @throws DbException if an error occurs while loading the properties file
     */
    private static Properties loadProperties() {
        try (FileInputStream fs = new FileInputStream("db.properties")) {
            Properties properties = new Properties();
            properties.load(fs);
            return properties;
        } catch (IOException e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * Closes the given {@link Statement}, if it is not {@code null}.
     * <p>
     *     If an {@link SQLException} occurs during the closing process,
     *     it is wrapped and rethrown as a {@link DbException}.
     * </p>
     *
     * @param st the {@link Statement} to be closed
     * @throws DbException if an error occurs while closing the statement
     */
    public static void closeStatement(Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }

    /**
     * Closes the given {@link ResultSet}, if it is not {@code null}.
     * <p>
     *     If an {@link SQLException} occurs during the closing process,
     *     it is wrapped and rethrown as a {@link DbException}.
     * </p>
     *
     * @param rs the {@link ResultSet} to be closed
     * @throws DbException if an error occurs while closing the set
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                throw new DbException(e.getMessage());
            }
        }
    }
}
