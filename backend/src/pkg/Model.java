package pkg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Model {
	private final String DBname = "mydb";
	private final String DBuser = "root";
	private final String DBpassword = "root";
	private final String DBpath = "localhost";
	private final int DBport = 3306;
	private final String connectionURL = "jdbc:mysql://localhost:3306/mydb?user=" + DBuser + "&password=" + DBpassword
			+ "&serverTimezone=UTC";;
	private Connection connection;
	private LinkedList listOfGoods = new LinkedList();

	// constructor
	// establishes connection with Database
	public Model() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		} catch (Exception ex) {
		}
		// checks if database is alive
		try (Connection connection = DriverManager.getConnection(this.connectionURL);) {
		} catch (Exception e) {
			System.out.println("Connection to Database '" + this.DBname + "' on " + this.DBpath + ":" + this.DBport
					+ " is unsuccessful");
			e.printStackTrace();
			return;
		}
		// if alive types to console and establishes connection
		try {
			this.connection = DriverManager.getConnection(this.connectionURL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection to Database '" + this.DBname + "' on " + this.DBpath + ":" + this.DBport
				+ " is successful");

		// filling linked list with existing Database values
		String query = "SELECT * FROM shop;";
		try (Statement statement = connection.prepareStatement(query);) {
			try (ResultSet result = statement.executeQuery(query);) {
				while (result.next()) {
					this.listOfGoods.add(result.getString(2));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// checks if Database is alive
	protected boolean checkStatus() {
		try (Connection connection = DriverManager.getConnection(this.connectionURL);) {
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	protected void printContents() {
		String query = "SELECT * FROM shop";
		try {
			Statement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void addGood(String goodName, int goodPrice, String goodCharacteristics, String category) {

		// add new good page to the filesystem
		File newPage = new File("C:/Users/sanja/workspace/TAProject/WebContent/" + goodName + ".html");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(newPage));) {
			newPage.createNewFile();

			String text = "<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "<meta charset=\"UTF-8\">\r\n"
					+ "<title>Shop</title>\r\n"
					+ "<link rel='stylesheet' type='text/css' href='style.css'>\r\n" + "</head>\r\n" + "<body>\r\n"
					+ "<div id='wrapper'>\r\n" + "</div>\r\n" + "<div id='header'>\r\n" + "</div>\r\n"
					+ "<div id='content'>\r\n" + "<h1>" + goodName + "</h1>\r\n" + "<h2>" + goodPrice + "</h2>\r\n";
			writer.write(text);

			for (String characteristic : goodCharacteristics.trim().split("\n")) {
				text = "<h3>" + characteristic + "</h3>\r\n";
				writer.write(text);
			}

			text = "</div>\r\n" + "<div id='footer'>\r\n" + "</div>\r\n" + "</body>\r\n" + "</html>";
			writer.write(text);

			// add new good to the Database
			String query = "INSERT INTO shop (good_name, good_price, good_characteristics, good_category, good_page_url) VALUES (?, ?, ?, ?, ?);";
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
				preparedStatement.setString(1, goodName);
				preparedStatement.setInt(2, goodPrice);
				preparedStatement.setString(3, goodCharacteristics);
				preparedStatement.setString(4, category);
				preparedStatement.setString(5, goodName + ".html");
				try {
					preparedStatement.executeUpdate();
					System.out.println("Successfully added to the DB");

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("Couldn't add to the DB");
					return;
				}

			} catch (SQLSyntaxErrorException e) {
				e.printStackTrace();
				return;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// updating LinkedList of goods with new data added
		if (this.listOfGoods.getIndexByValue(goodName) == -1) {
			this.listOfGoods.add(goodName);
		}
	}

	// kills connection
	protected void killDB() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Couldn't close the connection with " + this.DBname + " on " + this.connectionURL);
			return;
		}
		System.out.println("Successfully closed connection with " + this.DBname + " on " + this.connectionURL);
	}

	protected List<String> getCategory(String selectedCategory) {
		String query = "SELECT * FROM shop;";

		List<String> listOfCategories = new ArrayList<>();
		try (Statement stmnt = connection.prepareStatement(query);) {
			try (ResultSet rs = stmnt.executeQuery(query);) {
				while (rs.next()) {
					if (rs.getString(5).equals(selectedCategory)) {
						listOfCategories.add(rs.getString(2));
					}
				}
				return listOfCategories;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Can't find goods of specified category");
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Can't find goods of specified category");
			return null;
		}

	}

	protected List<String> searchByWord(String word) {
		return this.listOfGoods.search(word);

	}

}
