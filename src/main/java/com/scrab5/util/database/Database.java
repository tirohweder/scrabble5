package com.scrab5.util.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/* establish connection */

public class Database {

	protected Connection connection;
	protected Properties properties;

	public Database() {
		this.connect(System.getProperty("user.dir") + System.getProperty("file.separator") + "resources"
				+ System.getProperty("file.separator") + "transfermarkt.db");
		// c.setAutoCommit(false);
		properties = new Properties();
		properties.setProperty("PRAGMA foreign_keys", "ON");
	}

	// Stellt die Verbindung zur Datenbank mit dem Pfad "file" her
	private void connect(String file) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.connection = DriverManager.getConnection("jdbc:sqlite:" + file);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// Trennt die Verbindung zur Datenbank
	protected void disconnect() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
