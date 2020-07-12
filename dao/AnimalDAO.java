package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Animal;

public class AnimalDAO implements InterfaceDAO<Animal> {
	
	AnimalDAO() {
		
	}
	
	public void delete(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
			
		try {
			String SQL = "DELETE Animal WHERE id=?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        stmt.executeUpdate(); 			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao remover o animal por id", e);
		} finally {
			close(conn, stmt, null);
		}
	}
	
	public void update(Animal animal) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "UPDATE Animal SET animal = ? WHERE id=?";
			stmt = conn.prepareStatement(SQL);
	    	stmt.setString(1, animal.getAnimal());
	    	stmt.setInt(2, animal.getId());
	    	
	        stmt.executeUpdate();	
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao atualizar o animal", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public List<Animal> getAll() {
		Connection conn = DatabaseAccess.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		
		List<Animal> animals = new ArrayList<Animal>();
		
		try {
			stmt = conn.createStatement();
			
			String SQL = "SELECT * FROM Animal";
	        rs = stmt.executeQuery(SQL);
	        
	        while (rs.next()) {
	        	Animal a = getAnimalFromRs(rs);
	        	
	        	animals.add(a);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar todos os animais", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return animals;
	}
	
	public void insert(Animal animal) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
				
		try {
			String SQL = "INSERT INTO Animal (animal) VALUES (?)";
			stmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
	    	stmt.setString(1, animal.getAnimal());
	    	
			
	        stmt.executeUpdate();
	        
	        rs = stmt.getGeneratedKeys();
	        
	        if (rs.next()) {
	        	animal.setId(rs.getInt(1));
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao inserir o animal", e);
		} finally {
			close(conn, stmt, rs);
		}
				
	}
	
	public Animal getById(int id) {
		Connection conn = DatabaseAccess.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		Animal animal = null;
		
		try {
			String SQL = "SELECT * FROM Animal WHERE id = ?";
			stmt = conn.prepareStatement(SQL);
			stmt.setInt(1, id);
			
	        rs = stmt.executeQuery();
	        
	        while (rs.next()) {
	        	animal = getAnimalFromRs(rs);
	        }
			
		} catch (SQLException e) {
			throw new RuntimeException("Erro ao selecionar o animal por id", e);
		} finally {
			close(conn, stmt, rs);
		}
		
		return animal;		
	}
	
	private Animal getAnimalFromRs(ResultSet rs) throws SQLException {
		Animal a = new Animal();
		a.setId(rs.getInt("id"));
		a.setAnimal(rs.getString("animal"));
		
		return a;
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
