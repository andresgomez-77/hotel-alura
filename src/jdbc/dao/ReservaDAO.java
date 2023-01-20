package jdbc.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Reserva;

public class ReservaDAO {
	
	private Connection connection;
	
	public ReservaDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void guardar(Reserva reserva) {
		try {
			String sql = "INSERT INTO reservas (fecha_entrada, fecha_salida, Valor, FormaPago) VALUES (?, ?, ?, ?)";

			try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

				statement.setDate(1, reserva.getfechaE());
				statement.setDate(2, reserva.getfechaS());
				statement.setInt(3, reserva.getvalor());
				statement.setString(4, reserva.getformaPago());

				statement.executeUpdate();

				try (ResultSet resultSet = statement.getGeneratedKeys()) {
					while (resultSet.next()) {
						reserva.setId(resultSet.getInt(1));
					}
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public List<Reserva> buscar() {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {
			String sql = "SELECT idReserva, fecha_entrada, fecha_salida, Valor, FormaPago FROM reservas";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.execute();

				transformarResultSetEnReserva(reservas, statement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Reserva> buscarId(Integer id) {
		List<Reserva> reservas = new ArrayList<Reserva>();
		try {

			String sql = "SELECT idReserva, fecha_entrada, fecha_salida, Valor, FormaPago FROM reservas WHERE idReserva = ?";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {
				statement.setInt(1, id);
				statement.execute();

				transformarResultSetEnReserva(reservas, statement);
			}
			return reservas;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Eliminar(Integer id) {
		try (PreparedStatement statement = connection.prepareStatement("DELETE FROM reservas WHERE idReserva = ?")) {
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void Actualizar(Date fechaE, Date fechaS, String valor, String formaPago, Integer id) {
		try (PreparedStatement statement = connection
				.prepareStatement("UPDATE reservas SET fecha_entrada = ?, fecha_salida = ?, Valor = ?, FormaPago = ? WHERE idReserva = ?")) {
			statement.setDate(1, fechaE);
			statement.setDate(2, fechaS);
			statement.setString(3, valor);
			statement.setString(4, formaPago);
			statement.setInt(5, id);
			statement.execute();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
						
	private void transformarResultSetEnReserva(List<Reserva> reservas, PreparedStatement pstm) throws SQLException {
		try (ResultSet resultSet = pstm.getResultSet()) {
			while (resultSet.next()) {
				Reserva produto = new Reserva(resultSet.getInt(1), resultSet.getDate(2), resultSet.getDate(3), resultSet.getInt(4), resultSet.getString(5));

				reservas.add(produto);
			}
		}
	}
}
