package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdb.factory.ConnectionFactory;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class BuscarDAO {
	private Connection con;

	public BuscarDAO(Connection con) {
		this.con = con;
	}
	public List<Reserva> listarReservas() {
		final Connection con = new ConnectionFactory().recuperarConexion();
		List<Reserva> resultado = new ArrayList<>();
		try {
			PreparedStatement statement;
			statement = con.prepareStatement("SELECT * FROM reservas");
			try (statement) {
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				try (resultSet) {
					while (resultSet.next()) {
						Reserva fila = new Reserva(resultSet.getDate("FECHA_ENTRADA"), resultSet.getDate("FECHA_SALIDA"),
								resultSet.getInt("VALOR"), resultSet.getString("FORMAPAGO"));
						fila.setId(resultSet.getInt("IDRESERVA"));
						resultado.add(fila);
					}
				}
			}
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void eliminarPorReserva(Integer idReserva) {
		try {
			final PreparedStatement statement = con.prepareStatement("DELETE huespedes, reservas " + "FROM huespedes "
					+ "JOIN reservas ON huespedes.idReserva = reserva.idReserva " + "WHERE idReserva=?");
			try (statement) {
				statement.setInt(1, idReserva);
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void modificarReserva(Reserva reserva) {
		try {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE reserva SET fecha_entrada=? , fecha_salida=? , valor=? , FormaPago=? WHERE idReserva=?");
			try (statement) {
				statement.setDate(1, reserva.getfechaE());
				statement.setDate(2, reserva.getfechaS());
				statement.setInt(3, reserva.getvalor());
				statement.setString(4, reserva.getformaPago());
				statement.setInt(5, reserva.getId());
				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Huespedes> listarHuespedes() {
		final Connection con = new ConnectionFactory().recuperarConexion();
		List<Huespedes> resultado = new ArrayList<>();
		try (con) {
			final PreparedStatement statement = con.prepareStatement("SELECT * FROM huespedes");
			try (statement) {
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				try (resultSet) {
					while (resultSet.next()) {
						Huespedes fila = new Huespedes(resultSet.getInt("id"), resultSet.getString("nombre"),
								resultSet.getString("apellido"), resultSet.getDate("fecha_nacimiento"),
								resultSet.getString("nacionalidad"), resultSet.getString("telefono"),
								resultSet.getInt("idReserva"));
						resultado.add(fila);
					}
				}
			}
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void modificarHuesped(Huespedes huesped) {
		try {
			final PreparedStatement statement = con.prepareStatement(
					"UPDATE huespedes SET nombre=?, apellido=?, fecha_nacimiento=?,  nacionalidad=? ,telefono=? ,idReserva=? WHERE id=?");
			try (statement) {
				statement.setString(1, huesped.getNombre());
				statement.setString(2, huesped.getApellido());
				statement.setDate(3, huesped.getFechaNacimiento());
				statement.setString(4, huesped.getNacionalidad());
				statement.setString(5, huesped.getTelefono());
				statement.setInt(6, huesped.getIdReserva());
				statement.setInt(7, huesped.getId());

				statement.execute();
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Integer tomarNumeros(String palabraConNumeros) {
		StringBuilder numbersOnly = new StringBuilder();
		Integer numeroEncontrado;

		for (char c : palabraConNumeros.toCharArray()) {
			if (Character.isDigit(c)) {
				numbersOnly.append(c);
			}
		}

		try {
			numeroEncontrado = Integer.parseInt(String.valueOf(numbersOnly));
		} catch (NumberFormatException e) {
			return 0;
		}
		return numeroEncontrado;
	}

	public List<List> buscarPorPalabraClave(String text) {
		final Connection con = new ConnectionFactory().recuperarConexion();

		List<List> resultado = new ArrayList<>();
		List<Huespedes> huespedes = new ArrayList<>();
		List<Reserva> reservas = new ArrayList<>();
		try (con) {
			final PreparedStatement statement = con.prepareStatement("SELECT * " + "FROM huespedes " + "JOIN reservas "
					+ "ON huespedes.idReserva = reservas.idReserva "
					+ "Where nombre=? or apellido=? or fecha_nacimiento=? or  nacionalidad=? or telefono=? or fecha_entrada=? or fecha_salida=? or FormaPago=? or id=? or idReserva=?");
			try (statement) {
				statement.setString(1, text);
				statement.setString(2, text);
				statement.setString(3, text);
				statement.setString(4, text);
				statement.setString(5, text);
				statement.setString(6, text);
				statement.setString(7, text);
				statement.setString(8, text);
				statement.setInt(9, tomarNumeros(text));
				statement.setInt(10, tomarNumeros(text));
				statement.execute();
				final ResultSet resultSet = statement.getResultSet();
				try (resultSet) {
					// Integer id,String nombre, String apellido, String fechaNacimiento, String
					// nacionalidad, String telefono, Integer idReserva
					while (resultSet.next()) {
						Huespedes fila = new Huespedes(resultSet.getInt("id"), resultSet.getString("nombre"),
								resultSet.getString("apellido"), resultSet.getDate("fecha_nacimiento"),
								resultSet.getString("nacionalidad"), resultSet.getString("telefono"),
								resultSet.getInt("idReserva"));
						huespedes.add(fila);
						Reserva fila1 = new Reserva(resultSet.getDate("FECHA_ENTRADA"), resultSet.getDate("FECHA_SALIDA"),
								resultSet.getInt("VALOR"), resultSet.getString("FORMAPAGO"));
						fila1.setId(resultSet.getInt("IDRESERVA"));
						reservas.add(fila1);
					}
					resultado.add(huespedes);
					resultado.add(reservas);
				}
			}
			return resultado;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
