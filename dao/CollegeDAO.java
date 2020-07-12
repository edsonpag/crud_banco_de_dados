package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.College;

public class CollegeDAO implements InterfaceDAO<College> {
	
	CollegeDAO() {
		
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE College WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao remover a faculdade por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(College college) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE College SET name = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, college.getName());
	    	stmt.setInt(2, college.getId());
	    	
	        stmt.executeUpdate();	
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar a faculdade", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public List<College> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<College> colleges = new ArrayList<College>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM College";
	        rs = stmt.executeQuery(SQL);
	        
	        while (rs.next()) {
	        	College c = getCollegeFromRs(rs);
	        	
	        	colleges.add(c);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar todas as faculdades", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return colleges;
	}
	
	public void insert(College college) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "INSERT INTO College (name) VALUES (?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, college.getName());
	    	
			
	        stmt.executeUpdate();
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	college.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir a faculdade", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public College getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		College college = null;
		
		try {
			String SQL = "SELECT * FROM College WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	college = getCollegeFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar a faculade por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return college;		
	}
	
	private College getCollegeFromRs(ResultSet rs) throws SQLException {
		College c = new College();
		c.setId(rs.getInt("id"));
		c.setName(rs.getString("name"));
		
		return c;
	}
	
	private void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			if (rs != null) { rs.close(); }
			if (stmt != null) { stmt.close(); }
			if (conn != null) { conn.close(); }
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao fechar recursos.", e);
		}
	}
}
