package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

public class FillDatabase extends Database {
	private static PreparedStatement pstmPlayer;
	private static PreparedStatement pstmServer;
	private static PreparedStatement pstmDic;

	/* closes Statement streams */
	private void closeStatements() {
		try {
			if ((pstmServer != null) && (!pstmServer.isClosed())) {
				pstmServer.close();
			}
			if ((pstmDic != null) && (!pstmDic.isClosed())) {
				pstmDic.close();
			}
			if ((pstmPlayer != null) && (!pstmPlayer.isClosed())) {
				pstmPlayer.close();
			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Problem beim Schliessen der Prepared Statements aufgetreten.");
		}
	}

	// This method clears all entries from the Table in the parameter
	private void deleteTable(String name) {
		try {
			Statement statement = connection.createStatement();
			String sql = "DELETE FROM " + name + ';';
			statement.execute(sql);
		} catch (SQLException e) {
			System.out.println("Could not perform deletion in table " + name);
			System.out.println(e);
		}
	}

	/* creates the preparedStatements to insert something in a table */
	// Preparedstatements ändern und in fill methoden einfügen
	private void prepareStatements() {
		try {
			pstmPlayer = connection
					.prepareStatement("UPDATE Player " + "(Name, Picture, TotalPoints, PersonalHighscore, LaidWords, "
							+ "PointsPerWordRate, LongestWord, TotalPlayedGames, TotalWins, "
							+ "WinRate, FaveDic) VALUES (?,?,?,?,?,?,?,?,?,?,?);");
			pstmServer = connection.prepareStatement("INSERT INTO Server (ServerNames, Dictionaries,"
					+ "VictoryRanking, GameRanking, VictoryLossRate) VALUE (?,?,?,?,?);");
			pstmDic = connection.prepareStatement("INSERT INTO Letters (Letter, Points) VALUE (?,?);");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * method to fill table player at specific index/column. If variable for column
	 * name is from type integer, variable content is default.
	 */
	public static void updatePlayer(String column, String name, String contentString, int contentInt) {

		if (column == "Name") {
			String sql = "UPDATE Player SET Name = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				// set the corresponding param
				pstmt.setString(1, name);
				pstmt.setString(2, contentString);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "Picture") {
			String sql = "UPDATE Player SET Picture = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				// set the corresponding param
				pstmt.setString(2, contentString);
				pstmt.setString(1, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "TotalPoints") {
			String sql = "UPDATE Player SET PersonalHighscore = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "PersonalHighscore") {
			String sql = "UPDATE Player SET TotalPoints = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "LaidWords") {
			String sql = "UPDATE Player SET Laidwords = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "PointsPerWordRate") {
			String sql = "UPDATE Player SET PointsPerWordRate = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "LongestWord") {
			String sql = "UPDATE Player SET LongestWord = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "TotalPlayedGames") {
			String sql = "UPDATE Player SET TotalPlayedGames = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "TotalWins") {
			String sql = "UPDATE Player SET TotalWins = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "WinRate") {
			String sql = "UPDATE Player SET WinRate = ? , " + "WHERE Name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setInt(1, contentInt);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "FavDic") {
			String sql = "UPDATE Player SET FaveDic = ? , " + "WHERE name = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setString(1, contentString);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/*
	 * method to fill table player completely. Used when a new player profile is
	 * created. Variables for statistics get default values.
	 */
	public static void createPlayer(String name, String picture) {
		try {
			pstmPlayer.setString(1, name);
			pstmPlayer.setString(2, picture);
			pstmPlayer.setInt(3, 0);
			pstmPlayer.setInt(4, 0);
			pstmPlayer.setInt(5, 0);
			pstmPlayer.setInt(6, 0);
			pstmPlayer.setInt(7, 0);
			pstmPlayer.setInt(8, 0);
			pstmPlayer.setInt(9, 0);
			pstmPlayer.setInt(10, 0);
			pstmPlayer.setString(11, "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//This method updates the entries from the server table at specific column from a specific server
	public static void updateServer(String column, String name, String content) {

		if (column == "ServerNames") {
			String sql = "UPDATE Server SET ServerNames = ? , " + "WHERE ServerNames = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setString(1, name);
				pstmt.setString(2, content);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "Dictionaries") {
			String sql = "UPDATE Server SET Dictionaries = ? , " + "WHERE ServerNames = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setString(1, content);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "VictoryRanking") {
			String sql = "UPDATE Server SET VictoryRanking = ? , " + "WHERE ServerNames = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setString(1, content);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "GameRanking") {
			String sql = "UPDATE Server SET GameRanking = ? , " + "WHERE ServerNames = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setString(1, content);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} else if (column == "VictoryLossRate") {
			String sql = "UPDATE Server SET VictoryLossRate = ? , " + "WHERE ServerNames = ?";

			try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

				// set the corresponding param
				pstmt.setString(1, content);
				pstmt.setString(2, name);
				// update
				pstmt.executeUpdate();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/*
	 * method to fill table server completely. Used when a new server is created.
	 * Variables for statistics get default values.
	 */
	public static void createServer(String name) {
		try {
			pstmServer.setString(1, name);
			pstmServer.setString(2, "");
			pstmServer.setString(3, "");
			pstmServer.setString(4, "");
			pstmServer.setString(5, "");
			pstmServer.setString(6, "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fillLetters(String name) {
		// read from intake
	}

	/*
	 * for testing: public static void main(String[] args) { new
	 * FillDatabase().fillPlayer(…); }
	 */

}
