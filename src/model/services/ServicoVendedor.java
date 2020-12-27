package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.VendedorDao;
import model.entities.Vendedor;

public class ServicoVendedor {
	
	private VendedorDao dao = DaoFactory.createVendedorDao(); // injetor de dependência
	
	public List<Vendedor> findAll() { // recupera todos os vendedores 
		return dao.findAll();
	}
	
	//Inserir um novo departemento ou atualizar o departamento existente
	public void salvarOuAtualizar(Vendedor obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}
	
	public void remove(Vendedor obj) {
		dao.deleteById(obj.getId());
	}
}
