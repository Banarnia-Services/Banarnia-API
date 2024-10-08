package de.banarnia.api.sql;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class SQLite extends Database {

    private final File databaseFile;

    public SQLite(Logger logger, File databaseFile) {
        super(databaseFile.getAbsolutePath(), logger);
        this.databaseFile = databaseFile;
    }

    @Override
    public boolean openConnection(boolean silent) {
        if (!databaseFile.exists()) {
            databaseFile.getParentFile().mkdirs();
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                logger.warning("Failed to create database file: " + databaseFile.getAbsolutePath());
            }
        }

        try {
            if (connection != null && !connection.isClosed())
                return true;
        } catch (SQLException throwables) {
            if (!silent)
                throwables.printStackTrace();
            logger.warning("Error while connecting to database '" + databaseName + "'.");
            return false;
        }

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            if (!silent)
                e.printStackTrace();
            logger.warning("Cannot open connection to mysql server - jdbc driver missing.");
            return false;
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + databaseFile);
        } catch (SQLException throwables) {
            if (!silent)
                throwables.printStackTrace();
            logger.warning("Error while connecting to database '" + databaseName + "'.");
            return false;
        }

        return true;
    }
}
