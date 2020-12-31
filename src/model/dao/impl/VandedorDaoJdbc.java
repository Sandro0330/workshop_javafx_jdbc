package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.VendedorDao;
import model.entities.Departamento;
import model.entities.Vendedor;

public class VandedorDaoJdbc implements VendedorDao {
	
	private Connection conexao;
	
	public VandedorDaoJdbc(Connection conexao) {
		this.conexao = conexao;
	}

	@Override
	public void insert(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(
					"insert into seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId)"
					+ "Values "
					+ "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1,  obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3,  new java.sql.Date(obj.getDataNasc().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5,  obj.getDepartamento().getId());
			
			int linhaAfetadas = st.executeUpdate();	
			
			 if (linhaAfetadas > 0) {
				 ResultSet rs = st.getGeneratedKeys();
				 if (rs.next()) {
					 int id = rs.getInt(1);
					 obj.setId(id);
					 
				 }
				 DB.closeResultSet(rs);
			 }
			 else {
				 throw new DbException("ERRO inesperado! Nenhuma linha afetada!");
			 }
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}	
	}

	@Override
	public void update(Vendedor obj) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement(
					"UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE id = ? " );		
			
			st.setString(1,  obj.getNome());
			st.setString(2, obj.getEmail());
			st.setDate(3,  new java.sql.Date(obj.getDataNasc().getTime()));
			st.setDouble(4, obj.getSalarioBase());
			st.setInt(5,  obj.getDepartamento().getId());
			st.setInt(6, obj.getId());
			
			st.executeUpdate();			
		} 
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}	

		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conexao.prepareStatement("DELETE FROM seller WHERE Id = ?");
			
			st.setInt(1, id);
			
			int linha = st.executeUpdate();
			
			if(linha == 0) {
				throw new DbException("Não existe");
			
			}
			
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public Vendedor findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(
					"select seller. * ,department.Name as DepName "
					+ "from seller inner join department "
					+ "on seller.DepartmentId = department.Id "
					+ "where seller.Id = ? ");
			
			st.setInt(1, id);
			rs = st.executeQuery();
			if(rs.next()) {
				Departamento dep = instaciaDepartamento(rs);
				Vendedor obj = instanciaVandedor(rs, dep);
				return obj;
				
			}
			return null;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Vendedor instanciaVandedor(ResultSet rs, Departamento dep) throws SQLException {
		 Vendedor obj = new Vendedor();
		 obj.setId(rs.getInt("Id"));
		 obj.setNome(rs.getString("Name"));
		 obj.setEmail(rs.getString("Email"));
		 obj.setSalarioBase(rs.getDouble("BaseSalary"));
		 obj.setDataNasc(new java.util.Date(rs.getTimestamp("BirthDate").getTime()));//Instanciando java.util.Date
		 obj.setDepartamento(dep);
		 return obj;
	}

	private Departamento instaciaDepartamento(ResultSet rs) throws SQLException {	
		Departamento dep = new Departamento();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setNome(rs.getString("DepName"));
		return dep;
		
	}

	@Override
	public List<Vendedor> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(					
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name");
			
			rs = st.executeQuery();
			
			List<Vendedor> lista = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<Integer, Departamento>();
			
			while(rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instaciaDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor obj = instanciaVandedor(rs, dep);
				lista.add(obj);
				
			}
			return lista;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}

	}

	@Override
	public List<Vendedor> findByDepartament(Departamento departamento) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conexao.prepareStatement(					
					"SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name");
			
			st.setInt(1, departamento.getId());
			rs = st.executeQuery();
			
			List<Vendedor> lista = new ArrayList<>();
			Map<Integer, Departamento> map = new HashMap<Integer, Departamento>();
			
			while(rs.next()) {
				
				Departamento dep = map.get(rs.getInt("DepartmentId"));
				
				if(dep == null) {
					dep = instaciaDepartamento(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Vendedor obj = instanciaVandedor(rs, dep);
				lista.add(obj);
				
			}
			return lista;
		}
		catch(SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
	}	
}
