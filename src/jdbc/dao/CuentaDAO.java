package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jdbc.modelo.Cuenta;

public class CuentaDAO {
	private Connection con;

	public CuentaDAO(Connection con) {
		this.con = con;
	}

	public boolean confirmar(Cuenta cuenta) {
		try {
			PreparedStatement statement;
			statement = con.prepareStatement("SELECT CORREO, CONTRASEÑA FROM USUARIO WHERE CORREO =?");
			try (statement) {
				statement.setString(1, cuenta.getCorreo());
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				try (resultSet) {
					if (resultSet.next()) {
						return false;
					}
					Cuenta segCuenta = new Cuenta(resultSet.getString("CORREO"), resultSet.getString("CONTRASEÑA"));
					return segCuenta.equals(cuenta);
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
