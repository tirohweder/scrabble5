package com.scrab5.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import data.Player;
import io.CSVReader;

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

	/* deletes all entries from table "name" */
	private void deleteTable(String name) {

	}

	/* creates the preparedStatements to insert something in a table */
	private void prepareStatements() {
		try {
			pstmPlayer = connection.prepareStatement(
					"INSERT INTO Player " + "(Name, Picture, TotalPoints, PersonalHighscore, LaidWords, "
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
	public static void fillPlayer(int index, String name, String contentString, int contentInt) {
		try {
			switch (index) {
			case 1:
				pstmPlayer.setString(1, contentString);
				break;
			case 2:
				pstmPlayer.setString(2, contentString);
				break;
			case 3:
				pstmPlayer.setInt(3, contentInt);
				break;
			case 4:
				pstmPlayer.setInt(4, contentInt);
				break;
			case 5:
				pstmPlayer.setInt(5, contentInt);
				break;
			case 6:
				pstmPlayer.setInt(6, contentInt);
				break;
			case 7:
				pstmPlayer.setInt(7, contentInt);
				break;
			case 8:
				pstmPlayer.setInt(8, contentInt);
				break;
			case 9:
				pstmPlayer.setInt(9, contentInt);
				break;
			case 10:
				pstmPlayer.setInt(10, contentInt);
				break;
			case 11:
				pstmPlayer.setString(11, contentString);
				break;
			default:
				System.out.println("wrong index/column");
				break;
			}
			pstmPlayer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	/*
	 * method to fill table player completely. Used when a new player profile is
	 * created. Variables for statistics get default values.
	 */
	public static void fillPlayerComplete(String name, String picture) {
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

	/*
	 * method to fill table server at specific index/column.
	 */
	public static void fillServer(int index, String name, String content) {
		try {
			switch (index) {
			case 1:
				pstmServer.setString(1, content);
				break;
			case 2:
				pstmServer.setString(2, content);
				break;
			case 3:
				pstmServer.setString(3, content);
				break;
			case 4:
				pstmServer.setString(4, content);
				break;
			case 5:
				pstmServer.setString(5, content);
				break;
			case 6:
				pstmServer.setString(6, content);
				break;
			default:
				System.out.println("wrong index/column");
				break;
			}
			pstmPlayer.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * method to fill table server completely. Used when a new server is created.
	 * Variables for statistics get default values.
	 */
	public static void fillServerComplete(String name) {
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

	/* method to fill table dictionary from file */
	public static void fillLetters(String name) {
		// read from intake
	}

	/*
	 * for testing: public static void main(String[] args) { new
	 * FillDatabase().fillPlayer(…); }
	 */

}
