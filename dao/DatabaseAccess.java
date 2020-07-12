package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseAccess {
	private static final String connectionURL = "jdbc:sqlserver://localhost;databaseName=master;user=SA;password=Yugi0hGX15795";
	private static Connection conn = null;
	
	public static Connection getConnection() {
		try {
			if(conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(connectionURL);
			}
		} catch(SQLException ex) {
			throw new RuntimeException("Erro na conexão com o banco de dados", ex);
		}
		
		return conn;
	}
}
