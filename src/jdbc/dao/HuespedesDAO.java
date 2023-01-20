package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;


public class HuespedesDAO {
private Connection connection;
	
	public HuespedesDAO(Connection connection) {
		this.connection = connection;
	}
	
	public boolean guardar(Huespedes huesped) {
		try {
			String sql = "INSERT INTO huespedes (nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva) VALUES (?, ?, ?, ?,?,?)";

			try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setDate(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setInt(6, huesped.getIdReserva());

				statement.execute();
				return true;
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public List<Huespedes> listarHuespedes() {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {
			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva FROM huespedes";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();

				transformarResultSetEnHuesped(huespedes, statement);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Huespedes> buscarId(String id) {
		List<Huespedes> huespedes = new ArrayList<Huespedes>();
		try {

			String sql = "SELECT id, nombre, apellido, fecha_nacimiento, nacionalidad, telefono, idReserva FROM huespedes WHERE idReserva = ?";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setString(1, id);
				statement.execute();

				transformarResultSetEnHuesped(huespedes, statement);
			}
			return huespedes;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, Integer idReserva, Integer id) {
		try (PreparedStatement statement = connection
				.prepareStatement("UPDATE huespedes SET nombre = ?, apellido = ?, fecha_nacimiento = ?, nacionalidad = ?, telefono = ?, idReserva = ? WHERE id = ?")) {
			statement.setString(1, nombre);
			statement.setString(2, apellido);
			statement.setDate(3, fechaN);
			statement.setString(4, nacionalidad);
			statement.setString(5, telefono);
			statement.setInt(6, idReserva);
			statement.setInt(7, id);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	public void Eliminar(Integer id) {
		try (PreparedStatement statement = connection.prepareStatement("DELETE FROM huespedes WHERE id = ?")) {
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void transformarResultSetEnHuesped(List<Huespedes> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet resultSet = pstm.getResultSet()) {
			while (resultSet.next()) {
				Huespedes huespedes = new Huespedes(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4), resultSet.getString(5), resultSet.getString(6), resultSet.getInt(7));
				reservas.add(huespedes);
			}
		}				
	}
	
	
		
}

