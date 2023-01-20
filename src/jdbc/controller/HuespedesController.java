package jdbc.controller;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import jdb.factory.ConnectionFactory;
import jdbc.dao.HuespedesDAO;
import jdbc.modelo.Huespedes;


public class HuespedesController {
	 private HuespedesDAO huespedDAO;
	 
	 public HuespedesController() {
			Connection connection = new ConnectionFactory().recuperarConexion();
			this.huespedDAO = new HuespedesDAO(connection);
		}
	 
		public boolean guardar(Huespedes huespedes) {
			return huespedDAO.guardar(huespedes);
		}
		public List<Huespedes> listarHuespedes() {
			return this.huespedDAO.listarHuespedes();
		}
		
		public List<Huespedes> listarHuespedesId(String id) {
			return this.huespedDAO.buscarId(id);
		}
		
		public void actualizar(String nombre, String apellido, Date fechaN, String nacionalidad, String telefono, Integer idReserva, Integer id) {
			this.huespedDAO.Actualizar(nombre, apellido, fechaN, nacionalidad, telefono, idReserva, id);
		}
		
		public void Eliminar(Integer id) {
			this.huespedDAO.Eliminar(id);
		}
}
