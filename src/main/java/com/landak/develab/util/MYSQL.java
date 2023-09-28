package com.landak.develab.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class MYSQL implements AutoCloseable {

    final static Map<String, HikariDataSource> connections = new HashMap<>();

    public static void setupConnection(
            final String key,
            final String server,
            final String database,
            final String username,
            final String password) {

        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + server + "/" + database + "?useSSL=false&useUnicode=true&character_set_server=utf8mb4");
        config.setUsername(username);
        config.setPassword(password);
        config.setRegisterMbeans(true);
        config.setPoolName(key);

        final HikariDataSource ds = new HikariDataSource(config);
        ds.setMaximumPoolSize(10);
        ds.setMaxLifetime(60000);
        ds.setMinimumIdle(2);
        ds.setIdleTimeout(10000);
        ds.setLeakDetectionThreshold(10000);

        connections.put(key, ds);
    }

    final String key;
    final Connection connection;

    public MYSQL(final String key) throws SQLException {
        this.key = key;
        connection = connections.get(key).getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        Connection connection = getConnection();
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }

    public void execute(PreparedStatement statement) throws SQLException {
        statement.execute();
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception ignored) {
        }
    }

}
