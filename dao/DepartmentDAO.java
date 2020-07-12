package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Department;

public class DepartmentDAO implements InterfaceDAO<Department> {
	
	DepartmentDAO() {
		
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Department WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao remover o departamento por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(Department department) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Department SET name = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, department.getName());
	    	stmt.setInt(2, department.getId());
	    	
	        stmt.executeUpdate();	
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar o departamento", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public List<Department> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Department> departments = new ArrayList<Department>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM Department";
	        rs = stmt.executeQuery(SQL);
	        
	        while (rs.next()) {
	        	Department d = getDepartmentFromRs(rs);
	        	
	        	departments.add(d);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar todos os departamentos", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return departments;
	}
	
	public void insert(Department department) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "INSERT INTO Departamento (name) VALUES (?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, department.getName());
	    	
			
	        stmt.executeUpdate();
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	department.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir o departamento", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public Department getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Department department = null;
		
		try {
			String SQL = "SELECT * FROM Department WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	department = getDepartmentFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar o departamento por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return department;		
	}
	
	private Department getDepartmentFromRs(ResultSet rs) throws SQLException {
		Department d = new Department();
		d.setId(rs.getInt("id"));
		d.setName(rs.getString("name"));
		
		return d;
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
