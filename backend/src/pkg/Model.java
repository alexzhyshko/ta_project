package pkg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Model {
	private final String DBname = "mydb";
	private final String DBuser = "root";
	private final String DBpassword = "root";
	private final String DBpath = "localhost";
	private final int DBport = 3306;
	private final String connectionURL = "jdbc:mysql://localhost:3306/mydb?user=" + DBuser + "&password=" + DBpassword
			+ "&serverTimezone=UTC";;
	private Connection connection;

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
			System.out.println(
					"Connection to Database '" + this.DBname + "' on " + this.DBpath+":"+this.DBport + " is unsuccessful");
			e.printStackTrace();
			return;
		}
		// if alive types to console and establishes connection
		try {
			this.connection = DriverManager.getConnection(this.connectionURL);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connection to Database '" + this.DBname + "' on " + this.DBpath+":"+this.DBport + " is successful");
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
		String query="SELECT * FROM shop";
		try {
			Statement stmt = connection.prepareStatement(query);
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void addGood(String goodName, int goodPrice, String goodCharacteristics, String category) {
		File newPage = new File("C:/Users/sanja/workspace/TAProject/WebContent/"+goodName+".html");
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(newPage));){
			newPage.createNewFile();
			
			String text="<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" + 
					"<meta charset=\"UTF-8\">\r\n" + 
					"<title>Insert title here</title>\r\n" + 
					"<link rel='stylesheet' type='text/css' href='style.css'>\r\n"+
					"</head>\r\n" + 
					"<body>\r\n"+
					"<div id='wrapper'>\r\n"+
					"</div>\r\n"+
					"<div id='header'>\r\n"+
					"</div>\r\n"+
					"<div id='content'>\r\n"+
					"<h1>"+goodName+"</h1>\r\n"+
					"<h2>"+goodPrice+"</h2>\r\n";
					writer.write(text);
					
					for(String characteristic : goodCharacteristics.trim().split("\n")) {
					text = "<h3>"+characteristic+"</h3>\r\n";
					writer.write(text);
					}
					text = "</div>\r\n"+
					"<div id='footer'>\r\n"+
					"</div>\r\n"+
					"</body>\r\n" + 
					"</html>";
			writer.write(text);
			
			
		} catch (IOException e) {
			e.printStackTrace();
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

}
