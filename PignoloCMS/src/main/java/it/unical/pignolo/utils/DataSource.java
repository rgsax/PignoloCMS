package it.unical.pignolo.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class DataSource {
		private String dbURI = "jdbc:mariadb://chifacimudb.cki9ctegjpnd.eu-west-3.rds.amazonaws.com:3306/pignolocms";
	private String username = "chifacimu";
	private String password = "chifacimu";
	private static Object instance = null;
	
	
	public DataSource() { }
	
	public DataSource(String dbURI, String username, String password) {
		this.dbURI = dbURI;
		this.username = username;
		this.password = password;
	}
	
	public Connection getConnection() throws SQLException {
		if(instance == null) {			
			try {
				System.out.println("trying to instantiate driver");
				instance = Class.forName("org.mariadb.jdbc.Driver").newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		Connection connection = null;
		//System.out.println("connecting to " + dbURI);
		//connection = DriverManager.getConnection(dbURI + "?user=" + username + "&password=" + password);
		connection = DriverManager.getConnection(dbURI, username, password);
		if(connection == null)
			System.out.println("->connsection is null<-");
		return connection;
	}
	
	public void closeConnection() { }
	
	public static java.sql.Date getSQLDate(Date date) {
		return new java.sql.Date(date.getTime());
	}
	
	public static Date getDate(java.sql.Date date) {
		return new Date(date.getTime());
	}
}