package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.VandedorDaoJdbc;

public class DaoFactory {

	public static VendedorDao createVendedorDao() {
		return new VandedorDaoJdbc(DB.getConnection());
	}

	public static DepartamentoDao createDepartamentoDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
	
	
}
