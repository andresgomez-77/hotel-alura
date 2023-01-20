package jdbc.controller;

import jdb.factory.ConnectionFactory;
import jdbc.dao.CuentaDAO;
import jdbc.modelo.Cuenta;

public class CuentaController {
	private CuentaDAO cuentaDAO;

	public CuentaController() {
		this.cuentaDAO = new CuentaDAO(new ConnectionFactory().recuperarConexion());
	}

	public boolean confirmar(Cuenta cuenta) {
		return cuentaDAO.confirmar(cuenta);
	}
}
