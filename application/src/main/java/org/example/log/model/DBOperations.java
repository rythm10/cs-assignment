package org.example.log.model;

import org.example.log.util.SchedulerUtil;

import java.sql.*;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DBOperations {

    static Connection connection;
    static String connectionString = "jdbc:hsqldb:file:db-data/hsqldatabase";

    public DBOperations() {
    }

    public static void uploadToDatabase(List <LogData> dataList) throws Exception {

        ScheduledExecutorService
                executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new SchedulerUtil()::run, 0, 1, TimeUnit.SECONDS);
        runDbOperation(dataList);
        executorService.shutdown();


    }

    public static void runDbOperation(List <LogData> dataList) throws ClassNotFoundException, SQLException {

        Statement stmt;
        try {
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
        } catch (ClassNotFoundException exception) {
            throw exception;
        }
        try {
            connection = DriverManager.getConnection(connectionString, "SA", "");
            stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE if not exists LogData (EVENT_ID VARCHAR(50) NOT NULL, EVENT_DURATION INT NOT NULL, EVENT_TYPE VARCHAR(20) , EVENT_HOST VARCHAR(20) , EVENT_FLAG BIT DEFAULT FALSE NOT NULL);");
            int logCounter = 0;
            String SQLStatement = "INSERT INTO LogData VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(SQLStatement);

            for (LogData eventLog : dataList) {
                statement.setString(1, eventLog.getId());
                statement.setInt(2, eventLog.getEventDuration());
                statement.setString(3, eventLog.getType());
                statement.setString(4, eventLog.getHost());
                statement.setBoolean(5, eventLog.isAlertFlag());
                statement.executeUpdate();
                logCounter++;
            }
            System.out.println(logCounter + " rows inserted in database.");
        } catch (Exception e) {
        throw e;
    } finally {
        if (connection != null) {
            connection.close();
        }
    }
    }
}