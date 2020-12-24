package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartamentoDao;
import model.entities.Departamento;

public class ServicoDepartamento {
	
	private DepartamentoDao dao = DaoFactory.createDepartamentoDao();
	
	public List<Departamento> findAll() {
		return dao.findAll();
	}
	
	//Inserir um novo departemento ou atualizar o departamento existente
	public void salvarOuAtualizar(Departamento obj) {
		if (obj.getId() == null) {
			dao.insert(obj);
		} else {
			dao.update(obj);
		}
	}
	
	public void remove(Departamento obj) {
		dao.deleteById(obj.getId());
	}
}
