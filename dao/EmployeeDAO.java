package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Employee;

public class EmployeeDAO implements InterfaceDAO<Employee> {
	
	public void insert(Employee employee) {
		
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String SQL = "INSERT INTO Employee (name, age) VALUES (?, ?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, employee.getName());
			stmt.setInt(2, employee.getAge());
			
			stmt.executeUpdate();
			
			rs = stmt.getGeneratedKeys();
			
			if(rs.next()) {
				employee.setId(rs.getInt(1));
			}
			
		} catch(SQLException e) {
			throw new RuntimeException("Erro ao inserir", e);
		} finally {
			close(conn, stmt, rs);
		}
	}
	
	public void delete(int id) {
		
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Employee WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate();
	        
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao remover", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(Employee employee) {
		
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Employee SET name = ?, age = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, employee.getName());
	    	stmt.setInt(2, employee.getAge());

	    	stmt.setInt(3, employee.getId());
	    	
	        stmt.executeUpdate();		
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar dados", e);
		} finally {
			close(conn, stmt, rs);
		}
	}
	
	public List<Employee> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Employee> employees = new ArrayList<Employee>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM Employee";
	        rs = stmt.executeQuery(SQL);
	        
	        while (rs.next()) {
	        	Employee e = getEmployeeFromRs(rs);
	        	
	        	employees.add(e);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar todos", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return employees;
		
	}
	
	public Employee getById(int id) {
		
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Employee employee = null;
		
		try {
			String SQL = "SELECT * FROM Employee WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	employee = getEmployeeFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return employee;		
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
	
	private Employee getEmployeeFromRs(ResultSet rs) throws SQLException {
		Employee e = new Employee();
		e.setId(rs.getInt("id"));
		e.setName(rs.getString("name"));
		e.setAge(rs.getInt("age"));
		
		return e;
	}
	
	
}
