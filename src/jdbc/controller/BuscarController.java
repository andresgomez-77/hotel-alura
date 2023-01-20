package jdbc.controller;

import java.util.List;

import jdb.factory.ConnectionFactory;
import jdbc.dao.BuscarDAO;
import jdbc.modelo.Huespedes;
import jdbc.modelo.Reserva;

public class BuscarController {
    private BuscarDAO buscarDAO;

    public BuscarController() {
        this.buscarDAO = new BuscarDAO(new ConnectionFactory().recuperarConexion());
    }

    public List<Reserva> listarReservas() {
        return buscarDAO.listarReservas();
    }
    public List<Huespedes> listarHuespedes() {
        return buscarDAO.listarHuespedes();
    }
    public void eliminarPorReserva(Integer idReserva) {
    	buscarDAO.eliminarPorReserva(idReserva);
    }

    public void modificarReserva(Reserva reserva) {
    	buscarDAO.modificarReserva(reserva);
    }

    public void modificarHuesped(Huespedes huesped) {
    	buscarDAO.modificarHuesped(huesped);
    }

    public List<List> buscarPorPalabraClave(String text) {
        return buscarDAO.buscarPorPalabraClave(text);
    }
}
